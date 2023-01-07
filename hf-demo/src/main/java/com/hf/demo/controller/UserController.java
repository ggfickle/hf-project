package com.hf.demo.controller;

import com.hf.demo.aop.AopProxy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

/**
 * @author xiehongfei
 * @description
 * @date 2022/12/30 20:54
 */
@Slf4j
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping
    public String get() {
        BoundValueOperations<String, String> userCache = redisTemplate.boundValueOps("user");
        if (userCache.get() != null) {
            log.info("从缓存中获取数据");
            return userCache.get();
        }
        log.info("缓存中不存在，从DB中获取数据");
        // 可在此也调用add方法的Aop执行
        ((UserController) AopContext.currentProxy()).add();
        log.info("Get User");
        String userName = "James Xie";
        userCache.set(userName, 1, TimeUnit.HOURS);
        return userName;
    }

    @AopProxy
    @PostMapping
    public void add() {
        log.info("Add User");
    }

    @DeleteMapping
    public void delete() {
        redisTemplate.delete("user");
    }
}
