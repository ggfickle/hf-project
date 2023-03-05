package com.hf.kafka.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author xiehongfei
 * @description
 * @date 2023/3/5 15:44
 */
public class KafkaConstants {
    public final static String BOOTSTRAP_SERVERS = "localhost:9092";
}
