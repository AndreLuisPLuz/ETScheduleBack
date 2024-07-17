package ets.schedule.configurations;

import ets.schedule.filters.AuthFilter;
import ets.schedule.interfaces.services.*;
import ets.schedule.services.*;
import ets.schedule.sessions.UserSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;

@Configuration
public class DependenciesConfiguration {
    @Bean
    @Scope("singleton")
    PasswordService passwordService() {
        return new PBKDF2PasswordService();
    }

    @Bean
    @Scope("singleton")
    AuthService authService() {
        return new Auth0JwtService();
    }

    @Bean
    @Scope("singleton")
    AuthFilter authFilter() {
        return new AuthFilter();
    }

    @Bean
    @RequestScope
    UserSession userSession() {
        return new UserSession();
    }

    @Bean
    @Scope("singleton")
    UserService userService() {
        return new DefaultUserService();
    }

    @Bean
    @Scope("singleton")
    public EventsService eventsService() {
        return new DefaultEventsService();
    }

    @Bean
    @Scope("singleton")
    public GroupsService groupsService() {
        return new DefaultGroupService();
    }

    @Bean
    @Scope("singleton")
    public CoursesService coursesService() {
        return new DefaultCoursesService();
    }

    @Bean
    @Scope("singleton")
    public DisciplinesService disciplinesService() {
        return new DefaultDisciplinesService();
    }
}
