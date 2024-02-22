package kr.dogglezz.querycounter.query;

import org.hibernate.resource.jdbc.spi.StatementInspector;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;

import java.util.Objects;

@Component
public class QueryCounterInspector implements StatementInspector {
    private final QueryCounter queryCounter;

    public QueryCounterInspector(QueryCounter queryCounter) {
        this.queryCounter = queryCounter;
    }

    @Override
    public String inspect(String s) {
        if (isRequest()) {
            queryCounter.increment();
        }
        return s;
    }

    private boolean isRequest() {
        return Objects.nonNull(RequestContextHolder.getRequestAttributes());
    }
}
