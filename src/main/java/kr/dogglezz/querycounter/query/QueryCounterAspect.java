package kr.dogglezz.querycounter.query;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;

@Component
@Aspect
public class QueryCounterAspect {
    private final QueryCounter queryCounter;

    public QueryCounterAspect(QueryCounter queryCounter) {
        this.queryCounter = queryCounter;
    }

    @Around("execution(* javax.sql.DataSource.getConnection())")
    public Object getConnection(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object connection = proceedingJoinPoint.proceed();
        return Proxy.newProxyInstance(
                connection.getClass().getClassLoader(),
                connection.getClass().getInterfaces(),
                new ConnectionProxyHandler(connection, queryCounter)
        );
    }

}
