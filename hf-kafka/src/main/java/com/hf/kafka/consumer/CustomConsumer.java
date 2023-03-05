package com.hf.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.RoundRobinAssignor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * 消费者
 *
 * @author xiehongfei
 * @description
 * @date 2023/3/5 13:12
 */
@Slf4j
public class CustomConsumer {

    public static void main(String[] args) {
        // 配置
        Properties properties = new Properties();
        // 设置连接
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 设置反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // 设置消费者group_id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test");

        // 设置分区分配策略,消费组中其中一个消费者挂掉以后默认45秒后进行重新分区分配消费掉原分配到已挂掉节点的消息
        // 默认RangeAssignor
        // StickyAssignor
        // RoundRobinAssignor
        // CooperativeStickyAssignor
        properties.put(ConsumerConfig.PARTITION_ASSIGNMENT_STRATEGY_CONFIG, RoundRobinAssignor.class.getName());

        // 1 创建一个消费者
        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        // 2 订阅主题
        kafkaConsumer.subscribe(Collections.singletonList("first"));

        // 消费数据
        while (true) {
            // 每隔1S拉取一次数据
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
            consumerRecords.forEach(item -> System.out.println("消费者收到消息:" + item));
        }
    }
}
