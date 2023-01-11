package com.hf.mybatis.utils;

import com.hf.mybatis.enums.BaseEnum;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author xiehongfei
 * @description
 * @date 2023/1/11 18:49
 */
@Slf4j
public class EnumUtils {

    public static <T extends BaseEnum> T getEnumByCode(Class<T> enumClass, int code) {
        try {
            if (!enumClass.isEnum()) {
                log.info("{} is not an enum", enumClass.getName());
                return null;
            }
            T[] enumConstants = enumClass.getEnumConstants();
            Method getCode = enumClass.getMethod("getCode");
            for (T enumConstant : enumConstants) {
                if ((int) getCode.invoke(enumConstant) == code) {
                    log.info("解析到对应的枚举类型：{}, code: {}, message: {}", enumConstant,
                            enumConstant.getCode(), enumConstant.getMessage());
                    return enumConstant;
                }
            }
            log.info("未解析到对应的枚举类型, class name: {}, code: {}", enumClass.getName(), code);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            log.error("EnumUtils.getEnumByCode 执行失败：", e);
        }
        return null;
    }
}
