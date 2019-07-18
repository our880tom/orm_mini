package cn.swiftdev.example.orm.common.jdbc;

import cn.swiftdev.example.orm.common.Page;
import cn.swiftdev.example.orm.framework.QueryRule;

import java.util.List;
import java.util.Map;

public interface BaseDao<T, PK> {

    List<T> select(QueryRule queryRule) throws Exception;

    Page<?> select(QueryRule queryRule, int pageNo, int pageSize) throws Exception;

    List<Map<String, Object>> selectBySql(String sql, Object[] params) throws Exception;

    Page<Map<String, Object>> selectBySqlToPage(String sql, Object[] params, int pageNo, int pageSize) throws Exception;

    boolean delete(T entity) throws Exception;

    int deleteAll(List<T> list) throws Exception;

    PK insertAndReturnId(T entity) throws Exception;

    boolean insert(T entity) throws Exception;

    int insertAll(List<T> list) throws Exception;

    boolean update(T entity) throws Exception;
}
