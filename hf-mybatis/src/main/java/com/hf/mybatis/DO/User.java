package com.hf.mybatis.DO;

import com.hf.mybatis.enums.UserStatusEnum;
import lombok.Data;

import java.util.Date;

/**
 * @author xiehongfei
 * @description
 * @date 2023/1/10 21:43
 */
@Data
public class User {

    private int id;

    private String name;

    private String username;

    private String password;

    private UserStatusEnum status;

    private Date createTime;

    private Date updateTime;
}
