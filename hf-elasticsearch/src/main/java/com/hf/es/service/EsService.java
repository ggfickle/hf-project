package com.hf.es.service;

import com.hf.common.config.JacksonUtils;
import lombok.RequiredArgsConstructor;
import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xiehongfei
 * @description
 * @date 2023/2/20 22:27
 */
@Service
@RequiredArgsConstructor
public class EsService {

    private final RestHighLevelClient restHighLevelClient;


    public static void main(String[] args) throws Exception {
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
                RestClient.builder(new HttpHost("192.168.1.105", 9200, "http"))
        );
//        createIndex(restHighLevelClient);

//        insertIndexData(restHighLevelClient);

//        queryIndexData(restHighLevelClient);

//        updateIndexData(restHighLevelClient);

        deleteIndex(restHighLevelClient);
        restHighLevelClient.close();
    }

    /**
     * 创建索引
     * @param restHighLevelClient
     * @throws IOException
     */
    public static void createIndex(RestHighLevelClient restHighLevelClient) throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("user");
        // 设置Request参数
        createIndexRequest.settings(Settings.builder()
                .put("index.number_of_shards", 3) // 设置分区数
                .put("index.number_of_replicas", 2) // 设置副本数
        );
        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        System.out.println(JacksonUtils.writeValueAsString(createIndexResponse));
        if (createIndexResponse != null && createIndexResponse.isAcknowledged()) {
            System.out.println("USER INDEX CREATE SUCCESS");
        }
    }

    /**
     * 创建索引并且插入数据
     * @param restHighLevelClient
     * @throws IOException
     */
    public static void insertIndexData(RestHighLevelClient restHighLevelClient) throws IOException {
        IndexRequest posterRequest = new IndexRequest("poster");
        posterRequest.id("1");
        Map<String, String> map = new HashMap<>();
        map.put("user", "kimchy");
        map.put("postDate", "2013-01-30");
        map.put("message", "trying out Elasticsearch");
        String jsonValue = JacksonUtils.writeValueAsString(map);
        posterRequest.source(jsonValue, XContentType.JSON);
        IndexResponse indexResponse = restHighLevelClient.index(posterRequest, RequestOptions.DEFAULT);
        System.out.println(JacksonUtils.writeValueAsString(indexResponse));
        if (indexResponse != null && (indexResponse.getResult() == DocWriteResponse.Result.CREATED || indexResponse.getResult() == DocWriteResponse.Result.UPDATED)) {
            System.out.println(" indexResponse.getResult() :" + indexResponse.getResult());
            System.out.println("poster INDEX CREATE SUCCESS");
        }
    }

    /**
     * 查询
     * @param restHighLevelClient
     * @throws Exception
     */
    public static void queryIndexData(RestHighLevelClient restHighLevelClient) throws Exception {
        GetRequest getRequest = new GetRequest("poster", "1");
        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
        System.out.println(JacksonUtils.writeValueAsString(getResponse));
        System.out.println("getResponse.getIndex() = " + getResponse.getIndex());
        System.out.println("getResponse.getId() = " + getResponse.getId());
        System.out.println("getResponse.getType() = " + getResponse.getType());
        System.out.println("getResponse.getFields() = " + getResponse.getFields());
        if (getResponse.isExists()) {
            System.out.println("getResponse is exist");
            if (!getResponse.isSourceEmpty()) {
                System.out.println("getResponse.getSourceAsString() = " + getResponse.getSourceAsString());
            }
        } else {
            System.out.println("INDEX NOT EXIST");
        }
    }

    public static void updateIndexData(RestHighLevelClient restHighLevelClient) throws Exception {
        UpdateRequest updateRequest = new UpdateRequest("poster", "1");
        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("updated", new Date());
        jsonMap.put("reason", "daily update");
        updateRequest.doc(jsonMap);

        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
        System.out.println(JacksonUtils.writeValueAsString(updateResponse));
        if (updateResponse != null && updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
            System.out.println("update SUCCESS");
        }
    }

    public static void deleteIndex(RestHighLevelClient restHighLevelClient) throws IOException {
        DeleteRequest deleteRequest = new DeleteRequest("poster", "1");
        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
        System.out.println("JacksonUtils.writeValueAsString(deleteResponse) = " + JacksonUtils.writeValueAsString(deleteResponse));
        if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED) {
            System.out.println("DELETE SUCCESS");
        }
    }
 }
