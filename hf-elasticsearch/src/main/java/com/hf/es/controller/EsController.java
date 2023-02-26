package com.hf.es.controller;

import com.hf.es.entity.Book;
import com.hf.es.repo.EsBookRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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

    private final EsBookRepo esBookRepo;

    @PutMapping
    public void save(@RequestBody Book book) {
        esBookRepo.save(book);
    }

    @GetMapping("/{id}")
    public Book getById(@PathVariable("id") Integer id) {
        return esBookRepo.findById(id).orElseThrow(() -> new RuntimeException("Not Find"));
    }

    @GetMapping("/findByTitle")
    public List<Book> findByTitle(@RequestParam("title") String title) {
        return esBookRepo.findByTitle(title);
    }
}
