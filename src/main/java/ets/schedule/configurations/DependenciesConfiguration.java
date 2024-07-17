package ets.schedule.configurations;

import ets.schedule.interfaces.services.CoursesService;
import ets.schedule.interfaces.services.DisciplinesService;
import ets.schedule.interfaces.services.UserService;
import ets.schedule.services.DefaultCoursesService;
import ets.schedule.services.DefaultDisciplinesService;
import ets.schedule.services.DefaultUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class DependenciesConfiguration {

    @Bean
    @Scope("singleton")
    public CoursesService courseService() {
        return new DefaultCoursesService();
    }

    @Bean
    @Scope("singleton")
    public DisciplinesService disciplinesService() {
        return new DefaultDisciplinesService();
    }

    @Bean
    @Scope("singleton")
    public UserService userService() {
        return new DefaultUserService();
    }
}
