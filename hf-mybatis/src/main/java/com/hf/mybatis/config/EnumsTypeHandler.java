package com.hf.mybatis.config;

import com.hf.mybatis.enums.BaseEnum;
import com.hf.mybatis.utils.EnumUtils;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author xiehongfei
 * @description
 * @date 2023/1/10 23:06
 */
public class EnumsTypeHandler implements TypeHandler<BaseEnum> {

    private final Class<BaseEnum> type;

    public EnumsTypeHandler(Class<BaseEnum> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = type;
    }

    @Override
    public void setParameter(PreparedStatement preparedStatement, int i, BaseEnum baseEnum, JdbcType jdbcType) throws SQLException {
        preparedStatement.setInt(i, baseEnum.getCode());
    }

    @Override
    public BaseEnum getResult(ResultSet resultSet, String s) throws SQLException {
        int resultSetInt = resultSet.getInt(s);
        return EnumUtils.getEnumByCode(type, resultSetInt);
    }

    @Override
    public BaseEnum getResult(ResultSet resultSet, int i) throws SQLException {
        int resultSetInt = resultSet.getInt(i);
        return EnumUtils.getEnumByCode(type, resultSetInt);
    }

    @Override
    public BaseEnum getResult(CallableStatement callableStatement, int i) throws SQLException {
        int statementInt = callableStatement.getInt(i);
        return EnumUtils.getEnumByCode(type, statementInt);
    }
}
