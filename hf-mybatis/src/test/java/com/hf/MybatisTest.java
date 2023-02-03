package com.hf;

import com.hf.mybatis.DO.User;
import com.hf.mybatis.mapper.UserMapper;
import org.apache.ibatis.session.*;
import org.apache.ibatis.session.defaults.DefaultSqlSessionFactory;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * @author xiehongfei
 * @description
 * @date 2023/1/12 15:36
 */
@RunWith(MockitoJUnitRunner.class)
public class MybatisTest {

    public static void main(String[] args) {
        SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
        SqlSession sqlSession = builder.build(new Configuration()).openSession(ExecutorType.SIMPLE);
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        User mapperById = mapper.getById(1);
    }
}
