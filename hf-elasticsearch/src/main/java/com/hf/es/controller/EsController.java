package com.hf.es.controller;

import com.hf.es.entity.Book;
import com.hf.es.repo.EsBookRepo;
import com.hf.es.service.EsDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.List;

/**
 * @author xiehongfei
 * @description
 * @date 2023/2/25 19:51
 */
@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/book")
public class EsController {

    private final EsDataService esDataService;

    @PutMapping
    public void save(@RequestBody Book book) {
        esDataService.save(book);
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable("id") Integer id) {
        return esDataService.getById(id);
    }

    @GetMapping("/findByTitle")
    public List<Book> findByTitle(@RequestParam("title") String title) {
        return esDataService.findByTitle(title);
    }

    @GetMapping("/rt/{id}")
    public Book getByIdWithTemplate(@PathVariable("id") Integer id) {
        return esDataService.getByIdWithTemplate(id);
    }

    @PutMapping("/createIndex")
    public void createIndex() {
        esDataService.createIndex();
    }

    @DeleteMapping("/delIndex")
    public void deleteIndex() {
        esDataService.deleteIndex();
    }

    @PutMapping("/save")
    public void saveDoc() {
        esDataService.saveDoc();
    }

    @GetMapping("/findAll")
    public void findAll() {
        esDataService.findAll();
    }

    @PostMapping("/update")
    public void update() {
        esDataService.update();
    }

    @GetMapping("/existDoc")
    public void existDoc() {
        esDataService.existDoc();
    }

    @GetMapping("/queryForPage")
    public void queryForPage(String value,
                             int pageNum,
                             int pageSize) {
        esDataService.queryForPage(value, pageNum, pageSize);
    }

    @GetMapping("/selectBreakUpText")
    public void selectBreakUpText(String text) {
        esDataService.selectBreakUpText(text);
    }

    @GetMapping("/termQuery")
    public void termQuery(String value) {
        esDataService.termQuery(value);
    }

    @GetMapping("/matchQuery")
    public void matchQuery(String value) {
        esDataService.matchQuery(value);
    }

    @GetMapping("/matchAllQuery")
    public void matchAllQuery() {
        esDataService.matchAllQuery();
    }

    @GetMapping("/prefixQuery")
    public void prefixQuery(String value) {
        esDataService.prefixQuery(value);
    }

    @GetMapping("/wildcardQuery")
    public void wildcardQuery(String value) {
        esDataService.wildcardQuery(value);
    }

    @GetMapping("/rangeQuery")
    public void rangeQuery(String from, String to) throws ParseException {
        esDataService.rangeQuery(from, to);
    }
}
