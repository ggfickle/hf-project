package com.hf.mybatis.enums;

import lombok.Getter;


/**
 * @author xiehongfei
 * @description
 * @date 2023/1/11 18:38
 */
@Getter
public enum UserStatusEnum implements BaseEnum {
    ON(0, "在职"),
    OFF(1, "离职"),
    ;

    UserStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private final Integer code;

    private final String message;
}
