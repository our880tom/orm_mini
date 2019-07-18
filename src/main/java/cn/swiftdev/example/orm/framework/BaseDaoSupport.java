package cn.swiftdev.example.orm.framework;

import cn.swiftdev.example.orm.common.jdbc.BaseDao;

import java.io.Serializable;

public abstract class BaseDaoSupport<T extends Serializable, PK extends Serializable> implements BaseDao<T, PK> {
    
}
