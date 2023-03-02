package com.hf.kafka.produce;

import com.hf.common.utils.JacksonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

/**
 * 事务
 *
 * @author xiehongfei
 * @description
 * @date 2023/3/2 23:03
 */
@Slf4j
public class CustomProducerTransaction {

    public static void main(String[] args) {
        // 配置
        Properties properties = new Properties();
        // 设置连接
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        // 设置序列化
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        // 设置transactional.id
        properties.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "transaction01");

        // 1 创建kafka生产对象
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(properties);

        kafkaProducer.initTransactions();
        kafkaProducer.beginTransaction();
        try {
            for (int i = 0; i < 60; i++) {
                kafkaProducer.send(new ProducerRecord<>("first",
                                "xiehongfei" + i, "xiehongfei" + i),
                        (recordMetadata, e) -> {
                            if (e == null) {
                                System.out.println("send success");
                            } else {
                                System.out.println("send error " + e);
                            }
                        });
            }
            kafkaProducer.commitTransaction();
        } catch (Exception e) {
            log.error("exception: ", e);
            kafkaProducer.abortTransaction();
        } finally {
            kafkaProducer.close();
        }
    }
}
