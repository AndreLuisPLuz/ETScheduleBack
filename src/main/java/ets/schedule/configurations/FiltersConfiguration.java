package ets.schedule.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import ets.schedule.filters.AuthFilter;

@Configuration
public class FiltersConfiguration {
    @Autowired
    AuthFilter authFilter;

    @Bean
    @Scope("singleton")
    protected FilterRegistrationBean<AuthFilter> registerAuthFilter() {
        var registrationBean = new FilterRegistrationBean<AuthFilter>();

        registrationBean.setFilter(authFilter);
        registrationBean.addUrlPatterns(
            // "/api/v1/user/*",
            // "/api/v1/course/*",
            // "/api/v1/discipline/*",
            // "/api/v1/event/*",
            // "/api/v1/group/*",
            "/api/v1/report/*"
        );
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
