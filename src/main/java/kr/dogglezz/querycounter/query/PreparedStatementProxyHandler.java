package kr.dogglezz.querycounter.query;

import org.springframework.web.context.request.RequestContextHolder;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Objects;

public class PreparedStatementProxyHandler implements InvocationHandler {
    private static final List<String> JDBC_QUERY_METHOD =
            List.of("executeQuery", "execute", "executeUpdate");

    private final Object preparedStatement;
    private final QueryCounter queryCounter;

    public PreparedStatementProxyHandler(Object preparedStatement, QueryCounter queryCounter) {
        this.preparedStatement = preparedStatement;
        this.queryCounter = queryCounter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (JDBC_QUERY_METHOD.contains(method.getName()) && isRequest()) {
            queryCounter.increment();
        }
        return method.invoke(preparedStatement, args);
    }

    private boolean isRequest() {
        return Objects.nonNull(RequestContextHolder.getRequestAttributes());
    }
}
