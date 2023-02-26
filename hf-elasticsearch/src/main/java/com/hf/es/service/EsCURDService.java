//package com.hf.es.service;
//
//import com.hf.common.utils.JacksonUtils;
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.DocWriteResponse;
//import org.elasticsearch.action.bulk.BulkItemResponse;
//import org.elasticsearch.action.bulk.BulkRequest;
//import org.elasticsearch.action.bulk.BulkResponse;
//import org.elasticsearch.action.delete.DeleteRequest;
//import org.elasticsearch.action.delete.DeleteResponse;
//import org.elasticsearch.action.get.*;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.update.UpdateRequest;
//import org.elasticsearch.action.update.UpdateResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.client.indices.CreateIndexRequest;
//import org.elasticsearch.client.indices.CreateIndexResponse;
//import org.elasticsearch.common.settings.Settings;
//import org.elasticsearch.common.xcontent.XContentType;
//
//import java.io.IOException;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * @author xiehongfei
// * @description
// * @date 2023/2/20 22:27
// */
//public class EsCURDService {
//
//    public static void main(String[] args) throws Exception {
//        try (RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
//                RestClient.builder(new HttpHost("localhost", 9200, "http"))
//        )) {
//            //        createIndex(restHighLevelClient);
//
////        insertIndexData(restHighLevelClient);
////
////        queryIndexData(restHighLevelClient);
//
////        updateIndexData(restHighLevelClient);
//
////        deleteIndex(restHighLevelClient);
//
//            bulkRequest(restHighLevelClient);
//
//            multiGet(restHighLevelClient);
//
//        }
//    }
//
//    /**
//     * 创建文档
//     *
//     * @param restHighLevelClient
//     * @throws IOException
//     */
//    public static void createIndex(RestHighLevelClient restHighLevelClient) throws IOException {
//        CreateIndexRequest createIndexRequest = new CreateIndexRequest("user");
//        // 设置Request参数
//        createIndexRequest.settings(Settings.builder()
//                .put("index.number_of_shards", 3) // 设置分区数
//                .put("index.number_of_replicas", 2) // 设置副本数
//        );
//        CreateIndexResponse createIndexResponse = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
//        System.out.println(JacksonUtils.writeValueAsString(createIndexResponse));
//        if (createIndexResponse != null && createIndexResponse.isAcknowledged()) {
//            System.out.println("USER INDEX CREATE SUCCESS");
//        }
//    }
//
//    /**
//     * 创建文档并且插入数据
//     *
//     * @param restHighLevelClient
//     * @throws IOException
//     */
//    public static void insertIndexData(RestHighLevelClient restHighLevelClient) throws IOException {
//        IndexRequest posterRequest = new IndexRequest("poster");
//        posterRequest.id("2");
//        Map<String, String> map = new HashMap<>();
//        map.put("user", "xiehongfe");
//        map.put("postDate", "2013-01-30");
//        map.put("message", "trying out Elasticsearch");
//        String jsonValue = JacksonUtils.writeValueAsString(map);
//        posterRequest.source(jsonValue, XContentType.JSON);
//        IndexResponse indexResponse = restHighLevelClient.index(posterRequest, RequestOptions.DEFAULT);
//        System.out.println(JacksonUtils.writeValueAsString(indexResponse));
//        if (indexResponse != null && (indexResponse.getResult() == DocWriteResponse.Result.CREATED || indexResponse.getResult() == DocWriteResponse.Result.UPDATED)) {
//            System.out.println(" indexResponse.getResult() :" + indexResponse.getResult());
//            System.out.println("poster INDEX CREATE SUCCESS");
//        }
//    }
//
//    /**
//     * 查询
//     *
//     * @param restHighLevelClient
//     * @throws Exception
//     */
//    public static void queryIndexData(RestHighLevelClient restHighLevelClient) throws Exception {
//        GetRequest getRequest = new GetRequest("poster", "1");
//        GetResponse getResponse = restHighLevelClient.get(getRequest, RequestOptions.DEFAULT);
//        System.out.println(JacksonUtils.writeValueAsString(getResponse));
//        System.out.println("getResponse.getIndex() = " + getResponse.getIndex());
//        System.out.println("getResponse.getId() = " + getResponse.getId());
//        System.out.println("getResponse.getType() = " + getResponse.getType());
//        System.out.println("getResponse.getFields() = " + getResponse.getFields());
//        if (getResponse.isExists()) {
//            System.out.println("getResponse is exist");
//            if (!getResponse.isSourceEmpty()) {
//                System.out.println("getResponse.getSourceAsString() = " + getResponse.getSourceAsString());
//            }
//        } else {
//            System.out.println("INDEX NOT EXIST");
//        }
//    }
//
//    public static void updateIndexData(RestHighLevelClient restHighLevelClient) throws Exception {
//        UpdateRequest updateRequest = new UpdateRequest("poster", "1");
//        Map<String, Object> jsonMap = new HashMap<>();
//        jsonMap.put("updated", new Date());
//        jsonMap.put("reason", "daily update");
//        updateRequest.doc(jsonMap);
//
//        UpdateResponse updateResponse = restHighLevelClient.update(updateRequest, RequestOptions.DEFAULT);
//        System.out.println(JacksonUtils.writeValueAsString(updateResponse));
//        if (updateResponse != null && updateResponse.getResult() == DocWriteResponse.Result.UPDATED) {
//            System.out.println("update SUCCESS");
//        }
//    }
//
//    public static void deleteIndex(RestHighLevelClient restHighLevelClient) throws IOException {
//        DeleteRequest deleteRequest = new DeleteRequest("poster", "1");
//        DeleteResponse deleteResponse = restHighLevelClient.delete(deleteRequest, RequestOptions.DEFAULT);
//        System.out.println("JacksonUtils.writeValueAsString(deleteResponse) = " + JacksonUtils.writeValueAsString(deleteResponse));
//        if (deleteResponse.getResult() == DocWriteResponse.Result.DELETED) {
//            System.out.println("DELETE SUCCESS");
//        }
//    }
//
//    /**
//     * 批量查询
//     */
//    public static void multiGet(RestHighLevelClient restHighLevelClient) throws IOException {
//        MultiGetRequest multiGetRequest = new MultiGetRequest();
//        multiGetRequest.add("poster", "1");
//        multiGetRequest.add("poster", "2");
//
//        MultiGetResponse multiGetItemResponses = restHighLevelClient.mget(multiGetRequest, RequestOptions.DEFAULT);
//        for (MultiGetItemResponse multiGetItemResponse : multiGetItemResponses) {
//            System.out.println("multiGetItemResponse.getFailure() = " + multiGetItemResponse.getFailure());
//            GetResponse response = multiGetItemResponse.getResponse();
//            if (response.isExists()) {
//                System.out.println("response.getVersion() = " + response.getVersion());
//                System.out.println("response.getSourceAsString() = " + response.getSourceAsString());
//            }
//        }
//    }
//
//    public static void bulkRequest(RestHighLevelClient restHighLevelClient) throws IOException {
//        BulkRequest bulkRequest = new BulkRequest();
//        bulkRequest.add(new IndexRequest("bulk1").id("1")
//                .source(XContentType.JSON, "name", "xiehongfei", "age","12"));
//        bulkRequest.add(new IndexRequest("bulk1").id("2")
//                .source(XContentType.JSON, "name", "lisi", "age","16"));
//        bulkRequest.add(new UpdateRequest("bulk1", "2")
//        .doc(XContentType.JSON, "age", "19"));
//        BulkResponse bulkItemResponses = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
//        if (bulkItemResponses.hasFailures()) {
//            System.out.println("has failures!!!!!");
//            return;
//        }
//        for (BulkItemResponse bulkItemResponse : bulkItemResponses) {
//            DocWriteResponse itemResponse = bulkItemResponse.getResponse();
//            // 根据操作类型检测执行结果
//            switch (bulkItemResponse.getOpType()) {
//                case INDEX:
//                case CREATE:
//                    // 处理创建请求
//                    IndexResponse indexResponse = (IndexResponse) itemResponse;
//                    System.out.println(JacksonUtils.writeValueAsString(indexResponse));
//                    break;
//                case UPDATE:
//                    // 处理更新请求
//                    UpdateResponse updateResponse = (UpdateResponse) itemResponse;
//                    System.out.println(JacksonUtils.writeValueAsString(updateResponse));
//                    break;
//                case DELETE:
//                    // 处理删除请求
//                    DeleteResponse deleteResponse = (DeleteResponse) itemResponse;
//                    System.out.println(JacksonUtils.writeValueAsString(deleteResponse));
//            }
//        }
//    }
//}
