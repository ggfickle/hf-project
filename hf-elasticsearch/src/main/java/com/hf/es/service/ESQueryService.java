package com.hf.es.service;

import com.hf.common.utils.JacksonUtils;
import com.hf.es.entity.UserEntity;
import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.Cancellable;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;

import javax.naming.directory.SearchResult;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

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

//            createIndex(restHighLevelClient);

//            searchRequest(restHighLevelClient);

            matchQuery(restHighLevelClient);
        }

    }

    @SneakyThrows
    public static void createIndex(RestHighLevelClient restHighLevelClient) {
        IndexRequest indexRequest = new IndexRequest("user");
        indexRequest.id("1");

        UserEntity userEntity =
                new UserEntity()
                        .setName("李志")
                        .setAge(16)
                        .setAddress("南京");
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

    public static void searchRequest(RestHighLevelClient restHighLevelClient) throws IOException {
        SearchRequest searchRequest = new SearchRequest("user");
        // 创建SearchSourceBuilder，构建ES搜索
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        // 设置ES查询条件
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "xie");
        searchSourceBuilder.query(termQueryBuilder);
        // 设置分页参数，偏移从0开始
        searchSourceBuilder.from(0);
        // 设置分页参数，分页大小=5
        searchSourceBuilder.size(5);
        // 设置请求超时时间，60秒
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("searchResponse.status() = " + searchResponse.status());
        System.out.println("searchResponse.getTook() = " + searchResponse.getTook());
        SearchHits searchResponseHits = searchResponse.getHits();
        System.out.println("searchResponseHits.getTotalHits().value = " + searchResponseHits.getTotalHits().value);
        for (SearchHit searchResponseHit : searchResponseHits) {
            System.out.println("searchResponseHit.getSourceAsString() = " + searchResponseHit.getSourceAsString());
        }
    }

    public static void matchQuery(RestHighLevelClient restHighLevelClient) throws IOException {
        SearchRequest searchRequest = new SearchRequest();
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        QueryBuilder queryBuilder = null;

//        queryBuilder = matchAllQuery();
//        queryBuilder = matchQuery();
//        queryBuilder = matchPhraseQuery();
        queryBuilder = termQuery();

        searchSourceBuilder.query(queryBuilder);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        System.out.println("searchResponse.status() = " + searchResponse.status());
        System.out.println("searchResponse.getTook() = " + searchResponse.getTook());
        if (searchResponse.status() == RestStatus.OK) {
            System.out.println("searchResponse.getHits().getTotalHits().relation = " + searchResponse.getHits().getTotalHits().relation);
            System.out.println("searchResponse.getHits().getTotalHits().value = " + searchResponse.getHits().getTotalHits().value);
            for (SearchHit searchResponseHit : searchResponse.getHits()) {
                System.out.println("searchResponseHit.getIndex() = " + searchResponseHit.getIndex());
                System.out.println("searchResponseHit.docId() = " + searchResponseHit.docId());
                System.out.println("searchResponseHit.getId() = " + searchResponseHit.getId());
                System.out.println("searchResponseHit.getSourceAsString() = " + searchResponseHit.getSourceAsString());
            }
        }
    }

    /**
     * 匹配所有
     *
     * @return
     */
    public static QueryBuilder matchAllQuery() {
        return QueryBuilders.matchAllQuery();
    }

    /**
     * match:拆分成一个个关键词, 可查询到
     *
     * @return
     */
    public static QueryBuilder matchQuery() {
        return QueryBuilders.matchQuery("name", "志李");
    }

    /**
     * match:关键词作为一个整体进行搜索, 顺序也要一样
     *
     * @return
     */
    public static QueryBuilder matchPhraseQuery() {
        return QueryBuilders.matchPhraseQuery("name", "志李");
    }

    /**
     * 等值匹配
     *
     * @return
     */
    public static QueryBuilder termQuery() {
        TermQueryBuilder termQueryBuilder = null;
        // 查询不到,因为name的type为text,es已做分词处理,走倒序索引查询不到
        termQueryBuilder = QueryBuilders.termQuery("name", "李志");
        // 可查询到,加上.keyword即可在text上进行查询
        termQueryBuilder = QueryBuilders.termQuery("name.keyword", "李志");
        return termQueryBuilder;
    }
}
