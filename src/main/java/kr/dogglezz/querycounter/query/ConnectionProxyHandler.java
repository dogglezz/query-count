package kr.dogglezz.querycounter.query;

import org.springframework.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

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
            queryCounter.increment();
        }
        return invoke;
    }

    private boolean isPrepareStatement(Method method) {
        return PREPARE_STATEMENT_METHOD_NAME.equals(method.getName());
    }
}
