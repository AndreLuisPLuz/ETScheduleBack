package ets.schedule.configurations;

 import ets.schedule.filters.AuthFilter;
import ets.schedule.integrations.flask.DefaultFlaskIntegration;
import ets.schedule.interfaces.integrations.FlaskIntegration;
import ets.schedule.interfaces.services.*;
import ets.schedule.services.*;
import ets.schedule.sessions.UserSession;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;

import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class DependenciesConfiguration {
    @Bean
    @Scope("singleton")
    protected PasswordService passwordService() {
        return new PBKDF2PasswordService();
    }

     @Bean
     @Scope("singleton")
     protected AuthService authService() {
         return new Auth0JwtService();
     }

     @Bean
     @Scope("singleton")
     protected AuthFilter authFilter() {
         return new AuthFilter();
     }

    @Bean
    @RequestScope
    protected UserSession userSession() {
        return new UserSession();
    }

    @Bean
    @Scope("singleton")
    protected UserService userService() {
        return new DefaultUserService();
    }

    @Bean
    @Scope("singleton")
    protected EventsService eventsService() {
        return new DefaultEventsService();
    }

    @Bean
    @Scope("singleton")
    protected GroupsService groupsService() {
        return new DefaultGroupService();
    }

    @Bean
    @Scope("singleton")
    protected CoursesService coursesService() {
        return new DefaultCoursesService();
    }

    @Bean
    @Scope("singleton")
    protected DisciplinesService disciplinesService() {
        return new DefaultDisciplinesService();
    }

    @Bean
    @Scope("singleton")
    protected FlaskIntegration flaskIntegration() {
        return new DefaultFlaskIntegration();
    }

    @Bean
    @Scope("singleton")
    protected StudentReportService studentReportService() {
        return new DefaultStudentReportService();
    }

    @Bean
    @Scope("singleton")
    protected InstructorService instructorService() {
        return new DefaultInstructorService();
    }

    @Bean
    @Scope("singleton")
    protected ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    @Scope("singleton")
    protected CompetencesService competencesService() {
        return new DefaultCompetencesService();
    }
}
