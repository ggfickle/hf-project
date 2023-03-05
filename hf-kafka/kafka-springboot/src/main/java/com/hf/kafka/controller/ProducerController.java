package com.hf.kafka.controller;

import kafka.Kafka;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.bind.annotation.*;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/5 14:47
 */
@Slf4j
@RestController
@RequestMapping("/producer")
public class ProducerController {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @PostMapping("/sendData")
    public Boolean sendData(@RequestParam("data")String data) {
        ListenableFuture<SendResult<String, String>> listenableFuture =
                kafkaTemplate.send("first", 0, data, data);
        listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
            @Override
            public void onFailure(Throwable ex) {
                log.info("发送失败.",ex);
            }

            @Override
            public void onSuccess(SendResult<String, String> result) {
                log.info("发送成功,{}",result.toString());
            }
        });
        return Boolean.TRUE;
    }
}
