package com.hf.mybatis.service;

import com.hf.mybatis.DO.User;
import com.hf.mybatis.enums.UserStatusEnum;
import com.hf.mybatis.mapper.UserMapper;
import com.hf.mybatis.utils.JacksonUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiehongfei
 * @description
 * @date 2023/1/10 21:45
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public User getUserById(Integer id) {
        if (id == null || id < 0) {
            throw new IllegalArgumentException("id is not valid");
        }
        User user = userMapper.getById(id);
        if (user == null) {
            throw new RuntimeException("user not exist");
        }
        log.info("result:{}", JacksonUtils.writeValueAsString(user));
        return user;
    }

    public void updateUserStatus(User user) {
        if (user.getStatus() == null || user.getId() <= 0) {
            throw new IllegalArgumentException();
        }
        getUserById(user.getId());
        userMapper.updateUserStatus(user.getId(), user.getStatus().getCode());
    }
}
