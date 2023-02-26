//package com.hf.es.service;
//
//import com.hf.common.utils.JacksonUtils;
//import org.apache.http.HttpHost;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestClient;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.aggregations.AggregationBuilders;
//import org.elasticsearch.search.aggregations.Aggregations;
//import org.elasticsearch.search.aggregations.bucket.terms.Terms;
//import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
//import org.elasticsearch.search.aggregations.metrics.Avg;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//
//import java.io.IOException;
//
///**
// * @author xiehongfei
// * @description
// * @date 2023/2/25 19:12
// */
//public class EsGroupByService {
//
//    public static void main(String[] args) throws IOException {
//        try (RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
//                RestClient.builder(new HttpHost("192.168.1.108", 9200, "http"))
//        )) {
//            // 创建SearchRequest对象, 设置查询索引名=order
//            SearchRequest searchRequest = new SearchRequest("user");
//            // 通过SearchSourceBuilder构建搜索参数
//            SearchSourceBuilder builder = new SearchSourceBuilder();
//            // 通过QueryBuilders构建ES查询条件，这里查询所有文档，复杂的查询语句设置请参考前面的章节。
//            builder.query(QueryBuilders.matchAllQuery());
//            // 创建terms桶聚合，聚合名字=by_birthDate, 字段=birthDate，根据shop_id分组
//            TermsAggregationBuilder aggregationBuilder = AggregationBuilders.terms("by_age")
//                    .field("age");
//            // 嵌套聚合
//            // 设置Avg指标聚合，(非Keyword类型的字段不可聚合)
////            aggregationBuilder.subAggregation(AggregationBuilders.max("max_birth").field("birthDate"));
//            // 设置聚合查询
//            builder.aggregation(aggregationBuilder);
//
//            // 设置搜索条件
//            searchRequest.source(builder);
//            // 如果只想返回聚合统计结果，不想返回查询结果可以将分页大小设置为0
//            builder.size(0);
//
//            // 执行ES请求
//            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//
//            // 处理聚合查询结果
//            Aggregations aggregations = searchResponse.getAggregations();
//            // 根据by_shop名字查询terms聚合结果
//            Terms byShopAggregation = aggregations.get("by_age");
//
//            // 遍历terms聚合结果
//            for (Terms.Bucket bucket  : byShopAggregation.getBuckets()) {
//                // 因为是根据shop_id分组，因此可以直接将桶的key转换成int类型
//                int age = bucket.getKeyAsNumber().intValue();
//                System.out.println("bucket = " + JacksonUtils.writeValueAsString(bucket) + ", age:" + age);
//
//                // 根据avg_price聚合名字，获取嵌套聚合结果
////                Avg avg = bucket.getAggregations().get("max_birth");
////                // 获取平均价格
////                double avgPrice = avg.getValue();
////                System.out.println(avgPrice);
//            }
//        }
//    }
//}
