package com.hf.kafka.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.*;

/**
 * 消费者
 * 指定消费的offset
 * 消费某个时间段开始的offset
 *
 * @author xiehongfei
 * @description
 * @date 2023/3/5 13:12
 */
@Slf4j
public class CustomConsumerSeekTime {

    public static void main(String[] args) {
        // 配置
        Properties properties = new Properties();
        // 设置连接
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 设置反序列化
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        // 设置消费者group_id
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test1");

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

        // 获取分区信息
        Set<TopicPartition> topicPartitions = kafkaConsumer.assignment();
        // 保证分区分配方案已经制定完毕
        while (topicPartitions.size() == 0) {
            // 拉取一次数据保证分区分配完毕
            kafkaConsumer.poll(Duration.ofSeconds(1));
            topicPartitions = kafkaConsumer.assignment();
        }

        // 希望把事件转为对应的offset
        HashMap<TopicPartition, Long> topicPartitionLongHashMap = new HashMap<>();
        topicPartitions.forEach(topicPartition -> {
            // 获取一天前的分区节点
            topicPartitionLongHashMap.put(topicPartition, System.currentTimeMillis() - 24 * 3600 * 1000);
        });
        // 通过offsetsForTimes获取offsetMap
        Map<TopicPartition, OffsetAndTimestamp> topicPartitionOffsetAndTimestampMap = kafkaConsumer.offsetsForTimes(topicPartitionLongHashMap);

        // 指定消费的offset
        for (TopicPartition topicPartition : topicPartitions) {
            OffsetAndTimestamp offsetAndTimestamp = topicPartitionOffsetAndTimestampMap.get(topicPartition);
            kafkaConsumer.seek(topicPartition, offsetAndTimestamp.offset());
        }

        // 消费数据
        while (true) {
            // 每隔1S拉取一次数据
            ConsumerRecords<String, String> consumerRecords = kafkaConsumer.poll(Duration.ofSeconds(1));
            consumerRecords.forEach(item -> System.out.println("消费者收到消息:" + item));
        }
    }
}
