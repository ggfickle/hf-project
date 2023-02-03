package com.hf.consumer.controller;

import com.hf.common.api.UserService;
import com.hf.common.entity.UserEntity;
import org.apache.dubbo.config.annotation.DubboReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiehongfei
 * @description
 * @date 2023/2/3 21:38
 */
@RestController
public class ConsumerController {

    @DubboReference
    private UserService userService;

    @GetMapping("/test")
    public ResponseEntity<UserEntity> test() {
        UserEntity loginInfo = userService.getLoginInfo();
        return new ResponseEntity<>(loginInfo, HttpStatus.OK);
    }

}
