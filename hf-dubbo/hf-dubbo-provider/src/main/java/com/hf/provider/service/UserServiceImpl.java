package com.hf.provider.service;

import com.hf.common.api.UserService;
import com.hf.common.entity.UserEntity;
import org.apache.dubbo.config.annotation.DubboService;

/**
 * @author xiehongfei
 * @description
 * @date 2023/2/3 21:50
 */
@DubboService
public class UserServiceImpl implements UserService {

    private static final UserEntity userEntity;

    static {
        userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setPassword("admin123");
    }

    @Override
    public UserEntity getLoginInfo() {
        return userEntity;
    }
}
