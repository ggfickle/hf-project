package com.hf.kafka.produce;

import com.hf.common.utils.JacksonUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * @author xiehongfei
 * @description
 * @date 2023/2/28 23:06
 */
public class CustomProducer {

    public static void main(String[] args) {

        // 配置
        Properties properties = new Properties();
        // 设置连接
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 设置序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 关联自定义分区器
        properties.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartition.class.getName());
        // 收到ack确认,0:无需确认，1：需要leader确认，-1：需要leader和ISR队列确认，ALL：同-1
        properties.put(ProducerConfig.ACKS_CONFIG, "-1");
        // 重试次数
        properties.put(ProducerConfig.RETRIES_CONFIG, "3");

        // 提高生产吞吐量
        // 缓冲区大小
        properties.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 1024 * 1024 * 32);
        // 批次大小
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 1024 * 16);
        // linger.ms(多少毫秒后无新数据则发送)
        properties.put(ProducerConfig.LINGER_MS_CONFIG, 1);
        // 压缩
        properties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");

        // 解决同一分区内消息乱序问题：
        // max.in.flight.requests.per.connection设置小于等于5，因为最大缓存5个请求元数据，所以最大保证5个请求的数据的有序
        properties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);


        // 1 创建kafka生产对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        for (int i = 0; i < 500; i++) {
            // 异步发送数据
//            kafkaProducer.send(new ProducerRecord<>("first", "xiehongfei" + i));
            // 异步发送带回调
//            kafkaProducer.send(new ProducerRecord<>("first", "xiehongfei Callback" + i), (recordMetadata, e) -> {
//                System.out.println(JacksonUtils.writeValueAsString(recordMetadata));
//                System.out.println("recordMetadata.partition() = " + recordMetadata.partition());
//                System.out.println("recordMetadata.offset() = " + recordMetadata.offset());
//                System.out.println("recordMetadata.timestamp() = " + recordMetadata.timestamp());
//                System.out.println("topic=" + recordMetadata.topic() + ", partition=" + recordMetadata.partition());
//                if (e == null) {
//                    System.out.println("send success");
//                } else {
//                    System.out.println("send error " + e);
//                }
//            });
            int finalI = i;
            kafkaProducer.send(new ProducerRecord<>("first", "xiehongfei" + i, "xiehongfei" + i),
                    (recordMetadata, e) -> {
                        System.out.println("xiehongfei" + finalI);
                        System.out.println(JacksonUtils.writeValueAsString(recordMetadata));
                        System.out.println("recordMetadata.partition() = " + recordMetadata.partition());
                        System.out.println("recordMetadata.offset() = " + recordMetadata.offset());
                        System.out.println("recordMetadata.timestamp() = " + recordMetadata.timestamp());
                        System.out.println("topic=" + recordMetadata.topic() + ", partition=" + recordMetadata.partition());
                        if (e == null) {
                            System.out.println("send success");
                        } else {
                            System.out.println("send error " + e);
                        }
                    });


            // 同步发送
//        for (int i = 0; i < 100; i++) {
//            try {
//                RecordMetadata recordMetadata = kafkaProducer.send(new ProducerRecord<>("first", "xiehongfei" + i * 10)).get();
//                System.out.println("recordMetadata.offset() = " + recordMetadata.offset());
//                System.out.println("recordMetadata.timestamp() = " + recordMetadata.timestamp());
//                System.out.println("recordMetadata.topic() = " + recordMetadata.topic());
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//
//        }
        }
        kafkaProducer.close();
    }
}
