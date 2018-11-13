package cn.d1m.pandora.domain.mybatis.handler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Array;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by jone.wang on 2018/8/10.
 * Description:
 */
public abstract class StringTokenizerTypeHandler<T> extends BaseTypeHandler<T[]> {
    private Class<T> tClass;

    public StringTokenizerTypeHandler(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, T[] parameter, JdbcType jdbcType) throws SQLException {
        if (!ObjectUtils.isEmpty(parameter)) {
            final String value = Arrays
                    .stream(parameter)
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            ps.setString(i, value);
        } else {
            ps.setString(i, null);
        }
    }

    @Override
    public T[] getNullableResult(ResultSet resultSet, String columnName) throws SQLException {
        return toArray(resultSet.getString(columnName));
    }

    @Override
    public T[] getNullableResult(ResultSet resultSet, int columnIndex) throws SQLException {
        return toArray(resultSet.getString(columnIndex));
    }

    @Override
    public T[] getNullableResult(CallableStatement callableStatement, int columnIndex) throws SQLException {
        return toArray(callableStatement.getString(columnIndex));
    }

    T[] toArray(String columnValue) {
        if (Objects.isNull(columnValue)) {
            return createArray(0);
        }
        String[] values = columnValue.split(",");
        T[] array = createArray(values.length);
        for (int i = 0; i < values.length; i++) {
            array[i] = parseString(values[i]);
        }
        return array;
    }

    @SuppressWarnings("unchecked")
    T[] createArray(int size) {
        return (T[]) Array.newInstance(tClass, size);
    }

    abstract T parseString(String value);
}
