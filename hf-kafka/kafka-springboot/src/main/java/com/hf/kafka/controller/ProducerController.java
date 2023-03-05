package com.hf.kafka.controller;

import com.hf.kafka.service.KafkaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    private KafkaService kafkaService;

    @PostMapping("/sendData")
    public Boolean sendData(@RequestParam("data")String data) {
        kafkaService.sendMessageAsync("first", data);
        return Boolean.TRUE;
    }
}
