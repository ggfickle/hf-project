package com.hf.es.entity;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author xiehongfei
 * @description
 * @date 2023/2/21 21:23
 */
@Data
@Accessors(chain = true)
public class UserEntity {

    private String name;

    private int age;

    private String address;
}
