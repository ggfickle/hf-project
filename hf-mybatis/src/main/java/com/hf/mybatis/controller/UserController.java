package com.hf.mybatis.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hf.mybatis.DO.User;
import com.hf.mybatis.enums.UserStatusEnum;
import com.hf.mybatis.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

/**
 * @author xiehongfei
 * @description
 * @date 2023/1/10 21:54
 */
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public User getUserInfo(@PathVariable("id") Integer id) {
        return userService.getUserById(id);
    }

    @SneakyThrows
    @PostMapping("/updateUserStatus")
    public Map<String, Object> updateUserStatus(@RequestBody User user) {
        log.info("updateUserStatus param: id: {}, status: {}", user.getId(), user.getStatus());
        userService.updateUserStatus(user);
        return Collections.singletonMap("result", "update success");
    }
}
