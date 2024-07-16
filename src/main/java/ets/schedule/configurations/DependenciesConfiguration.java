package ets.schedule.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.annotation.RequestScope;

import ets.schedule.interfaces.services.AuthService;
import ets.schedule.interfaces.services.PasswordService;
import ets.schedule.interfaces.services.UserService;
import ets.schedule.services.Auth0JwtService;
import ets.schedule.services.DefaultUserService;
import ets.schedule.services.PBKDF2PasswordService;
import ets.schedule.sessions.UserSession;

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
    @RequestScope
    UserSession userSession() {
        return new UserSession();
    }

    @Bean
    @Scope("singleton")
    UserService userService() {
        return new DefaultUserService();
    }
}
