package com.hf.kafka.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.BatchErrorHandler;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.listener.ConsumerAwareErrorHandler;
import org.springframework.kafka.listener.ContainerProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/5 15:52
 */
@Slf4j
@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, String>> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String>
                factory = new ConcurrentKafkaListenerContainerFactory<>();
        // 设置消费者工厂
        factory.setConsumerFactory(consumerFactory());
        // 消费者组中线程数量
        factory.setConcurrency(3);
        // 拉取超时时间
        factory.getContainerProperties().setPollTimeout(3000);

        // 将过滤器添添加到参数中
//        factory.setRecordFilterStrategy(consumerRecord -> {
//            // 设置过滤器，只接收消息内容中包含 "test" 的消息
//            String value = consumerRecord.value();
//            if (value.contains("test")) {
//                System.err.println(consumerRecord.value());
//                // 返回 false 则接收消息
//                return false;
//            }
//            // 返回 true 则抛弃消息
//            return true;
//        });
        // 将单条消息异常处理器添加到参数中
        factory.setErrorHandler(new ConsumerAwareErrorHandler() {
            @Override
            public void handle(Exception thrownException, ConsumerRecord<?, ?> data, Consumer<?, ?> consumer) {
                log.error("单条消息异常");
            }
        });
        // 将批量消息异常处理器添加到参数中,设置AckMode.RECORD时不需要batchErrorHandler
        factory.setBatchErrorHandler(new BatchErrorHandler() {
            @Override
            public void handle(Exception thrownException, ConsumerRecords<?, ?> data) {
                log.error("批量消息异常");
            }
        });

        // 设置ACK模式(手动提交模式，这里有七种)
        // RECORD： 每处理完一条记录后提交。
        // BATCH(默认)： 每次poll一批数据后提交一次，频率取决于每次poll的调用频率。
        // TIME： 每次间隔ackTime的时间提交。
        // COUNT： 处理完poll的一批数据后并且距离上次提交处理的记录数超过了设置的ackCount就提交。
        // COUNT_TIME： TIME和COUNT中任意一条满足即提交。
        // MANUAL： 手动调用Acknowledgment.acknowledge()后，并且处理完poll的这批数据后提交。
        // MANUAL_IMMEDIATE： 手动调用Acknowledgment.acknowledge()后立即提交。
        // 如果设置 AckMode 模式为 MANUAL 或者 MANUAL_IMMEDIATE，则需要对监听消息的方法中，引入 Acknowledgment 对象参数，并调用 acknowledge() 方法进行手动提交
        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.BATCH);
        // 当使用批量监听器时需要设置为true,设置AckMode.RECORD时要设为false
        factory.setBatchListener(true);

        return factory;
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfigs());
    }

    @Bean
    public Map<String, Object> consumerConfigs() {
        Map<String, Object> propsMap = new HashMap<>();
        // Kafka地址
        propsMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaConstants.BOOTSTRAP_SERVERS);
        //配置默认分组，这里没有配置+在监听的地方没有设置groupId，多个服务会出现收到相同消息情况
        propsMap.put(ConsumerConfig.GROUP_ID_CONFIG, "defaultGroup");
        // 是否自动提交offset偏移量(默认true),设置false后需要再factory离设置setAckMode
        propsMap.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        // 自动提交的频率(ms)
//        propsMap.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "100");
        // Session超时设置
        propsMap.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "15000");
        // 键的反序列化方式
        propsMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // 值的反序列化方式
        propsMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        // offset偏移量规则设置：
        // (1)、earliest：当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，从头开始消费
        // (2)、latest：当各分区下有已提交的offset时，从提交的offset开始消费；无提交的offset时，消费新产生的该分区下的数据
        // (3)、none：topic各分区都存在已提交的offset时，从offset后开始消费；只要有一个分区不存在已提交的offset，则抛出异常
        propsMap.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        return propsMap;
    }
}
