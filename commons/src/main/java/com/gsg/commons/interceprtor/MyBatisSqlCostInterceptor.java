package com.gsg.commons.interceprtor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.session.ResultHandler;
import org.springframework.stereotype.Component;

import java.sql.Statement;
import java.util.Properties;

/**
 * 拦截器，拦截打印sql执行耗时
 * @author gaoshenggang
 * @date  2021/11/15 19:21
 */
@Intercepts({@Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "update", args = {Statement.class}),
        @Signature(type = StatementHandler.class, method = "batch", args = { Statement.class })})
@Component(value = "myBatisSqlCostInterceptor")
@Slf4j
public class MyBatisSqlCostInterceptor implements Interceptor {
    public static final String META_OBJECT_H      = "h";
    public static final String META_OBJECT_TARGET = "target";

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        long startTime=System.currentTimeMillis();
        StatementHandler statementHandler = (StatementHandler) invocation.getTarget();

        MetaObject metaStatementHandler = SystemMetaObject.forObject(statementHandler);
        metaStatementHandler = agentMetaObject(metaStatementHandler, META_OBJECT_H);
        metaStatementHandler = agentMetaObject(metaStatementHandler, META_OBJECT_TARGET);

        Object proceed = invocation.proceed();

        MappedStatement mappedStatement = (MappedStatement) metaStatementHandler.getValue("delegate.mappedStatement");
        log.info("The SQL request method is : [{}],execute the SQL cost {} ms",mappedStatement.getId(),System.currentTimeMillis()-startTime);
        return proceed;
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        //to do nothing
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
}
