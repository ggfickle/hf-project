package com.hf.es.service;

import com.hf.common.utils.JacksonUtils;
import com.hf.es.entity.Book;
import com.hf.es.repo.EsBookRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.DateUtils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.AnalyzeRequest;
import org.elasticsearch.client.indices.AnalyzeResponse;
import org.elasticsearch.index.query.*;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.IndexOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.Query;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.data.elasticsearch.core.query.UpdateResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * @author xiehongfei
 * @description
 * @date 2023/2/25 19:30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EsDataService {

    private final EsBookRepo esBookRepo;

    private final ElasticsearchRestTemplate elasticsearchRestTemplate;


    public Book getById(Integer id) {
        return esBookRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Find"));
    }

    public void save(Book book) {
        esBookRepo.save(book);
    }

    public List<Book> findByTitle(String title) {
        return esBookRepo.findByTitle(title);
    }

    public Book getByIdWithTemplate(int id) {
        return elasticsearchRestTemplate.get(String.valueOf(id), Book.class,
                IndexCoordinates.of("book"));
    }

    public void createIndex() {
        IndexOperations indexOperations = elasticsearchRestTemplate.indexOps(Book.class);
        if (indexOperations.exists()) {
            log.info("Book index already exist");
            log.info("indexOperations.getMapping() = " + indexOperations.getMapping());
            return;
        }
        log.info("Begin create Book Index");
        indexOperations.create();
        indexOperations.putMapping(Book.class);
        log.info("Create Book Index success");
        log.info("indexOperations.getMapping() = " + indexOperations.getMapping());
    }

    public void deleteIndex() {
        boolean delete = elasticsearchRestTemplate.indexOps(Book.class).delete();
        if (delete) {
            log.info("delete success");
            return;
        }
        log.info("delete fail");
    }

    public void saveDoc() {
        Book book1 = new Book()
                .setId(1)
                .setTitle("喜之郎1")
                .setAuthor("中国")
                .setPrice(12.2)
                .setCreateTime(Date.from(Instant.now()))
                .setUpdateTime(Date.from(Instant.now()));
        Book book2 = new Book()
                .setId(2)
                .setTitle("哈士奇1")
                .setAuthor("未知")
                .setPrice(14.2)
                .setCreateTime(Date.from(Instant.parse("2023-01-03T10:15:30.00Z")))
                .setUpdateTime(Date.from(Instant.parse("2023-01-04T10:15:30.00Z")));
        Book book3 = new Book()
                .setId(3)
                .setTitle("你说你1")
                .setAuthor("未知")
                .setPrice(13.2)
                .setCreateTime(Date.from(Instant.now()))
                .setUpdateTime(Date.from(Instant.now()));
        elasticsearchRestTemplate.save(book1, book2, book3);
        log.info("save success");
    }

    public void findAll() {
        elasticsearchRestTemplate
                .search(Query.findAll(), Book.class)
                .forEach(searchHit -> {
                    log.info(JacksonUtils.writeValueAsString(searchHit));
                });
    }

    public void update() {
        Book book = elasticsearchRestTemplate.get("3", Book.class);
        if (book == null) {
            throw new RuntimeException("not exist");
        }
        book = book.setUpdateTime(Date.from(Instant.now()));
        UpdateQuery updateQuery = UpdateQuery.builder(book.getId().toString())
                .withDocument(Document.parse(JacksonUtils.writeValueAsString(book)))
                .build();
        UpdateResponse updateResponse = elasticsearchRestTemplate.update(updateQuery, IndexCoordinates.of("book"));
        System.out.println("updateResponse.getResult().name() = " + updateResponse.getResult().name());
    }

    public void existDoc() {
        System.out.println("elasticsearchRestTemplate.exists = " + elasticsearchRestTemplate.exists("1", Book.class));
    }

    public void queryForPage(String value, int pageNum, int pageSize) {
        MatchQueryBuilder queryBuilder = QueryBuilders.matchQuery("title", value).analyzer("ik_smart");
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryBuilder);
        nativeSearchQuery.setPageable(PageRequest.of(pageNum == 0 ? 0 : pageNum - 1, pageSize));
        SearchHits<Book> bookSearchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Book.class);
        bookSearchHits.getSearchHits().forEach(searchHit -> {
            System.out.println("searchHit.getContent() = " + searchHit.getContent());
        });
        System.out.println("total = " + bookSearchHits.getTotalHits());
        System.out.println("pageNum = " + nativeSearchQuery.getPageable().getPageNumber());
        System.out.println("pageSize = " + nativeSearchQuery.getPageable().getPageSize());
        System.out.println("pages = " + (int) Math.ceil((double) bookSearchHits.getTotalHits() / nativeSearchQuery.getPageable().getPageSize()));
    }


    public void selectBreakUpText(String text) {
        RestHighLevelClient restHighLevelClient = elasticsearchRestTemplate.execute(client -> client);
        AnalyzeRequest analyzeRequest = AnalyzeRequest.buildCustomAnalyzer("ik_smart").build(text);
        try {
            AnalyzeResponse analyzeResponse = restHighLevelClient.indices().analyze(analyzeRequest, RequestOptions.DEFAULT);
            System.out.println("analyzeResponse.getTokens() = " + JacksonUtils.writeValueAsString(analyzeResponse.getTokens()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void termQuery(String value) {
        TermQueryBuilder termQueryBuilder =
                QueryBuilders.termQuery("author.keyword", value);
        commonSearch(termQueryBuilder);
    }


    public void matchQuery(String value) {
        // Operator.OR(并集) [默认] 只要分的词有一个和索引字段上对应上则就返回
        // Operator.AND(交集)   分的词全部满足的数据返回
        MatchQueryBuilder matchQueryBuilder = QueryBuilders.matchQuery("title", value).analyzer("ik_smart")
                .operator(Operator.OR);
        commonSearch(matchQueryBuilder);
    }

    public void matchAllQuery() {
        MatchAllQueryBuilder matchAllQuery =
                QueryBuilders.matchAllQuery();
        commonSearch(matchAllQuery);
    }

    public void prefixQuery(String value) {
        // text也能用:索引库字段分词后 分的词前缀要是能匹配也是可以返回此数据
        QueryBuilder queryBuilder =
                QueryBuilders.prefixQuery("title", value);
        commonSearch(queryBuilder);
    }

    public void wildcardQuery(String value) {
        // "*华*" 包含华字的
        //"华*" 华字后边零个或多个字符
        //"华?" 华字后边一个字符
        //"*华"或"?华" 会引发全表（全索引）扫描 注意效率问题
        // eg:喜?郎
        QueryBuilder queryBuilder =
                QueryBuilders.wildcardQuery("title", value);
        commonSearch(queryBuilder);
    }

    public void rangeQuery(String from, String to) throws ParseException {
        QueryBuilder queryBuilder =
                QueryBuilders.rangeQuery("updateTime")
                        .format("yyyy-MM-dd HH:mm:ss")
                        .from(from)
                        .to(to);
        commonSearch(queryBuilder);
    }

    private void commonSearch(QueryBuilder queryBuilder) {
        NativeSearchQuery nativeSearchQuery = new NativeSearchQuery(queryBuilder);
        SearchHits<Book> bookSearchHits = elasticsearchRestTemplate.search(nativeSearchQuery, Book.class);
        System.out.println("bookSearchHits = " + JacksonUtils.writeValueAsString(bookSearchHits));
    }
}
