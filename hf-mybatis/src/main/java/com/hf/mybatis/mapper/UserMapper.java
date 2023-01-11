package com.hf.mybatis.mapper;

import com.hf.mybatis.DO.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @author xiehongfei
 * @description
 * @date 2023/1/10 21:45
 */
public interface UserMapper {

    User getById(@Param("id") int id);
}
