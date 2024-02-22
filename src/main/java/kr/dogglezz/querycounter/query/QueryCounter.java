package kr.dogglezz.querycounter.query;

import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

@Component
@RequestScope
public class QueryCounter {
    private int count;

    public void increment() {
        count++;
    }

    public int getCount() {
        return count;
    }
}
