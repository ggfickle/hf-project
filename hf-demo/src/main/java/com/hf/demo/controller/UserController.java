package com.hf.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiehongfei
 * @description
 * @date 2022/12/30 20:54
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @GetMapping()
    public String get() {
        log.info("Get User");
        return "James Xie";
    }

    @PostMapping()
    public void add() {
        log.info("Add User");
    }
}
