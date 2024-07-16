package ets.schedule.configurations;

import ets.schedule.interfaces.services.CourseService;
import ets.schedule.interfaces.services.UserService;
import ets.schedule.services.DefaultCourseService;
import ets.schedule.services.DefaultUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DependenciesConfiguration {

    @Bean
    @Scope("singleton")
    public CourseService courseService() {
        return new DefaultCourseService();
    }

    @Bean
    @Scope("singleton")
    public UserService userService() {
        return new DefaultUserService();
    }
}
