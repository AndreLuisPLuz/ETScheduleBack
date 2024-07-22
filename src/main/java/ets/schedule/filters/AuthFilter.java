package ets.schedule.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;

import com.auth0.jwt.interfaces.DecodedJWT;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.enums.ProfileRole;
import ets.schedule.interfaces.services.AuthService;
import ets.schedule.sessions.UserSession;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class AuthFilter implements Filter {
    @Autowired
    AuthService authService;

    @Autowired
    UserSession userSession;

    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain)
        throws
            IOException,
            ServletException {

        var req = (HttpServletRequest) request;
        var res = (HttpServletResponse) response;

        String auth;
        try {
            auth = req.getHeader("Authorization");
        } catch (NullPointerException ex) {
            throw new ApplicationException(400, "Missing authorization header.");
        }

        DecodedJWT decodedJWT;
        decodedJWT = authService.decodeTokenAsync(auth);

        var userId = decodedJWT.getClaim("userId").asString();
        var profileId = decodedJWT.getClaim("profileId").asString();
        var profileRole = ProfileRole.getRole(
            decodedJWT.getClaim("role").asString()
        );

        userSession.setUserId(Long.valueOf(userId));
        userSession.setProfileId(Long.valueOf(profileId));
        userSession.setProfileRole(profileRole);

        chain.doFilter(req, res);
    }
}