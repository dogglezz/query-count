package kr.dogglezz.querycounter.query;

import org.hibernate.cfg.JdbcSettings;
import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfiguration {

    private final QueryCounterInspector queryCounterInspector;

    public HibernateConfiguration(QueryCounterInspector queryCounterInspector) {
        this.queryCounterInspector = queryCounterInspector;
    }

    @Bean
    public HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return hibernateProperties ->
                hibernateProperties.put(JdbcSettings.STATEMENT_INSPECTOR, queryCounterInspector);
    }
}
