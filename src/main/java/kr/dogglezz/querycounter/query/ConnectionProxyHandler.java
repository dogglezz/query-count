package kr.dogglezz.querycounter.query;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ConnectionProxyHandler implements InvocationHandler {
    private static final String PREPARE_STATEMENT_METHOD_NAME = "prepareStatement";

    private final Object target;
    private final QueryCounter queryCounter;

    public ConnectionProxyHandler(Object target, QueryCounter queryCounter) {
        this.target = target;
        this.queryCounter = queryCounter;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object invoke = method.invoke(target, args);
        if (isPrepareStatement(method)) {
            return Proxy.newProxyInstance(
                    invoke.getClass().getClassLoader(),
                    invoke.getClass().getInterfaces(),
                    new PreparedStatementProxyHandler(invoke, queryCounter));
        }
        return invoke;
    }

    private boolean isPrepareStatement(Method method) {
        return PREPARE_STATEMENT_METHOD_NAME.equals(method.getName());
    }
}
