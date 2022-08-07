package com.gsg.commons.interceprtor;

import com.gsg.commons.utils.Page;
import com.gsg.commons.utils.SearchBean;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.util.Map;
import java.util.Properties;

/**
 * 拦截器，拦截mybatis sql,添加分页参数
 * @author gaoshenggang
 * @date  2021/11/15 19:20
 */
@Slf4j
@Intercepts({
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query",
                args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = StatementHandler.class, method = "prepare",
                args = {Connection.class, Integer.class})})
@Component(value = "myBatisPageInterceptor")
public class MyBatisPageInterceptor implements Interceptor {
    public static final String META_OBJECT_H      = "h";
    public static final String META_OBJECT_TARGET = "target";

    /**
     * 设置配置项
     *
     * @param properties
     */
    @Override
    public void setProperties(Properties properties) {
        //to do nothing
    }

    /**
     * cover query for list
     * oracle转postgreSQL相关修改：1.select * from() 2.ROWNUM 转成 row_number() over()
     * @author gaoshenggang
     * @param sql
     * @return
     */
    public static String buildQuerySql(String sql, Page page) {
        sql = sql.toLowerCase();
        String temSql = sql;

        /*防止查询有显示排序的时候，排序被分页打乱，处理一下*/
//        if (sql.contains("order by")){
//            temSql = sql.split("order by")[0];
//            String orderSql = sql.split("order by")[1];
//            return "SELECT * FROM (SELECT * FROM (" + temSql + ") as TEMP limit " + page.offset() + "," + (page.endOffset()-page.offset()) + ") A order by " + orderSql;
//        }
        return temSql + " limit " + page.offset() + "," + (page.endOffset()-page.offset());
    }

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        metaStatementHandler = agentMetaObject(metaStatementHandler, META_OBJECT_H);
        metaStatementHandler = agentMetaObject(metaStatementHandler, META_OBJECT_TARGET);

        BoundSql boundSql = (BoundSql) metaStatementHandler.getValue("delegate.boundSql");
        Object parameterObject = boundSql.getParameterObject();


        if (null != parameterObject) {
            if (parameterObject instanceof MapperMethod.ParamMap) {
                Object page = ((Map) parameterObject).getOrDefault("page", null);
                if (page instanceof Page ) {
                    String sql = boundSql.getSql();
                    metaStatementHandler.setValue("delegate.boundSql.sql", buildQuerySql(sql, (Page) page));
                    metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
                    metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
                }
            }else if(parameterObject instanceof SearchBean) {
                SearchBean searchBean = (SearchBean) parameterObject;
                if (null != searchBean.getPage() && null != searchBean.getPage().getIndex()
                        && null != searchBean.getPage().getSize()){
                    String sql = boundSql.getSql();
                    metaStatementHandler.setValue("delegate.boundSql.sql", buildQuerySql(sql, searchBean.getPage()));
                    metaStatementHandler.setValue("delegate.rowBounds.offset", RowBounds.NO_ROW_OFFSET);
                    metaStatementHandler.setValue("delegate.rowBounds.limit", RowBounds.NO_ROW_LIMIT);
                }
            }
        }
        return invocation.proceed();
    }

    /**
     * 当目标类是StatementHandler类型时，才包装目标类，否者直接返回目标本身,减少目标被代理的
     *
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        if (target instanceof StatementHandler) {
            return Plugin.wrap(target, this);
        }
        return target;
    }

    /**
     * 分离代理对象链
     *
     * @param metaStatementHandler
     * @param name
     * @return
     */
    private MetaObject agentMetaObject(MetaObject metaStatementHandler, String name) {
        while (metaStatementHandler.hasGetter(name)) {
            Object object = metaStatementHandler.getValue(name);
            metaStatementHandler = SystemMetaObject.forObject(object);
        }
        return metaStatementHandler;
    }

    /**
     * 获取分页参数
     *
     * @param parameterObject
     * @return
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    private SearchBean getSearchBeanByParameter(Object parameterObject) {
        if (parameterObject == null) {
            return null;
        }

        if (parameterObject instanceof SearchBean) {
            return (SearchBean) parameterObject;
        }

        if (parameterObject instanceof Map) {
            Map<String, Object> param = (Map<String, Object>) parameterObject;
            return new SearchBean((Page) param.get("page"));
        }
        return null;
    }
}
