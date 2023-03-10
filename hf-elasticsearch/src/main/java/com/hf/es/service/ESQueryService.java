//package com.hf.es.service;
//
//import com.hf.common.utils.JacksonUtils;
//import com.hf.es.entity.UserEntity;
//import lombok.SneakyThrows;
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.ActionListener;
//import org.elasticsearch.action.index.IndexRequest;
//import org.elasticsearch.action.index.IndexResponse;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.unit.TimeValue;
//import org.elasticsearch.common.xcontent.XContentType;
//import org.elasticsearch.index.query.QueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.index.query.TermQueryBuilder;
//import org.elasticsearch.rest.RestStatus;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.SearchHits;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author xiehongfei
// * @description
// * @date 2023/2/21 20:59
// */
//
//public class ESQueryService {
//
//    public static void main(String[] args) throws Exception {
//        try (RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
//                RestClient.builder(new HttpHost("192.168.1.108", 9200, "http"))
//        )) {
//
////            createIndex(restHighLevelClient);
//
////            searchRequest(restHighLevelClient);
//
//            commonQuery(restHighLevelClient);
//        }
//
//    }
//
//    @SneakyThrows
//    public static void createIndex(RestHighLevelClient restHighLevelClient) {
//        IndexRequest indexRequest = new IndexRequest("user");
//        indexRequest.id("2");
//
//        UserEntity userEntity =
//                new UserEntity()
//                        .setName("??????")
//                        .setAge(11)
//                        .setAddress("??????")
//                        .setBirthDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2023-03-01 00:00:00"));
//        indexRequest.source(JacksonUtils.writeValueAsString(userEntity), XContentType.JSON);
//
//        // ????????????
////        restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
//
//        // ????????????
//        restHighLevelClient.indexAsync(indexRequest, RequestOptions.DEFAULT,
//                new ActionListener<IndexResponse>() {
//                    @Override
//                    public void onResponse(IndexResponse indexResponse) {
//                        System.out.println(JacksonUtils.writeValueAsString(indexResponse));
//                        System.out.println("user index " + indexResponse.getResult().getLowercase() + " success");
//                    }
//
//                    @Override
//                    public void onFailure(Exception e) {
//                        System.out.println("user index create fail, " + e);
//                    }
//                });
//
//        Thread.sleep(2000);
//    }
//
//    public static void searchRequest(RestHighLevelClient restHighLevelClient) throws IOException {
//        SearchRequest searchRequest = new SearchRequest("user");
//        // ??????SearchSourceBuilder?????????ES??????
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        // ??????ES????????????
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "xie");
//        searchSourceBuilder.query(termQueryBuilder);
//        // ??????????????????????????????0??????
//        searchSourceBuilder.from(0);
//        // ?????????????????????????????????=5
//        searchSourceBuilder.size(5);
//        // ???????????????????????????60???
//        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//        searchRequest.source(searchSourceBuilder);
//
//        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//        System.out.println("searchResponse.status() = " + searchResponse.status());
//        System.out.println("searchResponse.getTook() = " + searchResponse.getTook());
//        SearchHits searchResponseHits = searchResponse.getHits();
//        System.out.println("searchResponseHits.getTotalHits().value = " + searchResponseHits.getTotalHits().value);
//        for (SearchHit searchResponseHit : searchResponseHits) {
//            System.out.println("searchResponseHit.getSourceAsString() = " + searchResponseHit.getSourceAsString());
//        }
//    }
//
//    public static void commonQuery(RestHighLevelClient restHighLevelClient) throws IOException, ParseException {
//        SearchRequest searchRequest = new SearchRequest();
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        QueryBuilder queryBuilder = null;
//
////        queryBuilder = matchAllQuery();
////        queryBuilder = matchQuery();
////        queryBuilder = matchPhraseQuery();
////        queryBuilder = termQuery();
////        queryBuilder = termsQuery();
////        queryBuilder = rangeQuery();
//        queryBuilder = boolQuery();
//
//        searchSourceBuilder.query(queryBuilder);
//        searchRequest.source(searchSourceBuilder);
//        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//        System.out.println("searchResponse.status() = " + searchResponse.status());
//        System.out.println("searchResponse.getTook() = " + searchResponse.getTook());
//        if (searchResponse.status() == RestStatus.OK) {
//            System.out.println("searchResponse.getHits().getTotalHits().relation = " + searchResponse.getHits().getTotalHits().relation);
//            System.out.println("searchResponse.getHits().getTotalHits().value = " + searchResponse.getHits().getTotalHits().value);
//            for (SearchHit searchResponseHit : searchResponse.getHits()) {
//                System.out.println("searchResponseHit.getIndex() = " + searchResponseHit.getIndex());
//                System.out.println("searchResponseHit.docId() = " + searchResponseHit.docId());
//                System.out.println("searchResponseHit.getId() = " + searchResponseHit.getId());
//                System.out.println("searchResponseHit.getSourceAsString() = " + searchResponseHit.getSourceAsString());
//            }
//        }
//    }
//
//    /**
//     * ????????????
//     *
//     * @return
//     */
//    public static QueryBuilder matchAllQuery() {
//        return QueryBuilders.matchAllQuery();
//    }
//
//    /**
//     * match:???????????????????????????, ????????????
//     *
//     * @return
//     */
//    public static QueryBuilder matchQuery() {
//        return QueryBuilders.matchQuery("name", "??????");
//    }
//
//    /**
//     * match:???????????????????????????????????????, ??????????????????
//     *
//     * @return
//     */
//    public static QueryBuilder matchPhraseQuery() {
//        return QueryBuilders.matchPhraseQuery("name", "??????");
//    }
//
//    /**
//     * ????????????
//     *
//     * @return
//     */
//    public static QueryBuilder termQuery() {
//        TermQueryBuilder termQueryBuilder = null;
//        // ????????????,??????name???type???text,es??????????????????,???????????????????????????
//        termQueryBuilder = QueryBuilders.termQuery("name", "??????");
//        // ????????????,??????.keyword?????????text???????????????
//        termQueryBuilder = QueryBuilders.termQuery("name.keyword", "??????");
//        return termQueryBuilder;
//    }
//
//    /**
//     * ??????in??????
//     *
//     * @return
//     */
//    public static QueryBuilder termsQuery() {
//        return QueryBuilders.termsQuery("name.keyword", "?????????", "??????");
//    }
//
//    public static QueryBuilder rangeQuery() throws ParseException {
//        return QueryBuilders.rangeQuery("age")
//                .gte(12)
//                .lte(16);
//    }
//
//    /**
//     * ????????????
//     * @return
//     */
//    public static QueryBuilder boolQuery() {
//        return QueryBuilders.boolQuery()
//                .must(QueryBuilders.termQuery("name.keyword", "?????????"))
//                .should(QueryBuilders.rangeQuery("age").gte(12));
//    }
//}
