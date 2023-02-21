package com.hf.es.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
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
        RestClientBuilder restClientBuilder = RestClient.builder(new HttpHost("localhost", 9200, "http"));
        restClientBuilder.setRequestConfigCallback(
                requestConfigBuilder -> {
                    return requestConfigBuilder
                            .setConnectTimeout(10000) // 设置连接超时时间，5秒
                            .setSocketTimeout(60000); // 设置请求超时时间，1分种
                });
        restClientBuilder.setHttpClientConfigCallback(
                httpClientBuilder -> httpClientBuilder.setDefaultIOReactorConfig(
                IOReactorConfig.custom()
                        .setIoThreadCount(10) // 设置线程数
                        .build()));
        return new RestHighLevelClient(restClientBuilder);
    }
}
