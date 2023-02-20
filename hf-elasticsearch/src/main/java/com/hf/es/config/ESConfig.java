package com.hf.es.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiehongfei
 * @description
 * @date 2023/2/20 22:19
 */
@Slf4j
@Configuration
public class ESConfig {

    @Bean(destroyMethod = "close", name = "restHighLevelClient")
    public RestHighLevelClient createEsClientBean() {
       return new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.1.105", 9200, "http"))
        );
    }
}
