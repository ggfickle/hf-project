package com.hf.kafka.produce;

import com.hf.common.utils.JacksonUtils;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.ExecutionException;

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
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.1.108:9092");
        // 设置序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // 1 创建kafka生产对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        for (int i = 0; i < 5000000; i++) {
            // 异步发送数据
            kafkaProducer.send(new ProducerRecord<>("first", "xiehongfei" + i));
            // 异步发送带回调
            kafkaProducer.send(new ProducerRecord<>("first", "xiehongfei Callback" + i), (recordMetadata, e) -> {
                System.out.println(JacksonUtils.writeValueAsString(recordMetadata));
                System.out.println("recordMetadata.offset() = " + recordMetadata.offset());
                System.out.println("recordMetadata.timestamp() = " + recordMetadata.timestamp());
                System.out.println("topic=" + recordMetadata.topic() + ", partition=" + recordMetadata.partition());
                if (e == null) {
                    System.out.println("send success");
                } else {
                    System.out.println("send error " + e);
                }
            });
        }

        // 同步发送
        for (int i = 0; i < 100; i++) {
            try {
                RecordMetadata recordMetadata = kafkaProducer.send(new ProducerRecord<>("first", "xiehongfei" + i * 10)).get();
                System.out.println("recordMetadata.offset() = " + recordMetadata.offset());
                System.out.println("recordMetadata.timestamp() = " + recordMetadata.timestamp());
                System.out.println("recordMetadata.topic() = " + recordMetadata.topic());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }

        }
        kafkaProducer.close();
    }
}
