package com.hf.common.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.TimeZone;

/**
 * @author xiehongfei
 * @description
 * @date 2023/1/12 13:04
 */
public class JacksonUtils {

    private static final ObjectMapper objectMapper;

    static {
        objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
        objectMapper.setTimeZone(TimeZone.getTimeZone(ZoneId.of("GMT+8")));
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    public static ObjectMapper getObjectMapperInstance() {
        return objectMapper;
    }

    @SneakyThrows
    public static <T> T readValueFromString(String jsonString, Class<T> tClass) {
        return objectMapper.readValue(jsonString, tClass);
    }

    @SneakyThrows
    public static <T> T readValueFromTypeRef(String jsonString, TypeReference<T> tTypeReference) {
        return objectMapper.readValue(jsonString, tTypeReference);
    }

    @SneakyThrows
    public static String writeValueAsString(Object object) {
        return objectMapper.writeValueAsString(object);
    }
}
