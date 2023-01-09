package com.hf.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

/**
 * @author xiehongfei
 * @description
 * @date 2023/1/2 19:07
 */
@Service
public class AServiceImpl implements AService{

    @Override
    public Map<String, Object> getAInfo() {
        return Collections.singletonMap("name", "谢鸿飞");
    }
}
