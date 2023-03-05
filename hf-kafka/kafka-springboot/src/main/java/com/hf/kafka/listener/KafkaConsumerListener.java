package com.hf.kafka.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/5 15:01
 */
@Slf4j
@Configuration
public class KafkaConsumerListener {

    @KafkaListener(topics = "first")
    public void consumerTopic(String msg) {
        log.info("spring-kafka收到消息：" + msg);
    }
}
