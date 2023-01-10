package com.hf.demo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiehongfei
 * @description
 * @date 2023/1/9 20:07
 */

@Slf4j
@RestController
@RequiredArgsConstructor
public class KafkaController {

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @GetMapping("/send/{input}")
    public void sendFoo(@PathVariable String input) {
        this.kafkaTemplate.send("topic_input", input);
    }

    @KafkaListener(id = "webGroup", topics = "topic_input")
    public void listen(String input) {
        log.info("input value: {}" , input);
    }
}
