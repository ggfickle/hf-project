package com.hf.mybatis.service;

import com.hf.mybatis.DO.User;
import com.hf.mybatis.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xiehongfei
 * @description
 * @date 2023/1/10 21:45
 */
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
        return user;
    }
}
