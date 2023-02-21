package com.hf.es.service;

import com.hf.common.utils.JacksonUtils;
import com.hf.es.entity.UserEntity;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;

import java.io.IOException;

/**
 * @author xiehongfei
 * @description
 * @date 2023/2/21 20:59
 */

public class ESQueryService {

    public static void main(String[] args) throws IOException {
        try (RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("localhost", 9200, "http"))
        )) {

            createIndex(restHighLevelClient);
        }

    }

    @SneakyThrows
    public static void createIndex(RestHighLevelClient restHighLevelClient) {
        IndexRequest indexRequest = new IndexRequest("user");
        indexRequest.id("user");

        UserEntity userEntity =
                new UserEntity()
                        .setName("xie")
                        .setAge(12)
                        .setAddress("hangzhou");
        indexRequest.source(JacksonUtils.writeValueAsString(userEntity), XContentType.JSON);

        // 同步创建
//        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);

        // 异步创建
        restHighLevelClient.indexAsync(indexRequest, RequestOptions.DEFAULT,
                new ActionListener<IndexResponse>() {
                    @Override
                    public void onResponse(IndexResponse indexResponse) {
                        System.out.println("user index create success");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        System.out.println("user index create fail, " + e);
                    }
                });

        Thread.sleep(2000);
    }
}
