package com.hf.es.repo;

import com.hf.es.entity.Book;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author xiehongfei
 * @description
 * @date 2023/2/25 19:35
 */
public interface EsBookRepo extends ElasticsearchRepository<Book, Integer> {

    List<Book> findByTitle(String title);

    List<Book> findByTitleOrAuthor(String title, String author);

    List<Book> findByPriceLessThanEqualAndPriceGreaterThanEqual(@Param("from") Double from,
                                                                @Param("to") Double to);
}
