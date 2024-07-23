 package ets.schedule.filters;

 import java.io.IOException;

 import org.springframework.beans.factory.annotation.Autowired;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.responses.ErrorResponse;
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

    @Autowired
    ObjectMapper objMapper;

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
            var errorResponse = new ErrorResponse("Missing Authorization header.");

            res.setStatus(400);
            res.getOutputStream().print(objMapper.writeValueAsString(errorResponse));

            return;
        }

        DecodedJWT decodedJWT;
        try {
            decodedJWT = authService.decodeTokenAsync(auth);
        } catch (ApplicationException ex) {
            var errorResponse = new ErrorResponse("Invalid token.");

            res.setStatus(403);
            res.getOutputStream().print(objMapper.writeValueAsString(errorResponse));

            return;
        }

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