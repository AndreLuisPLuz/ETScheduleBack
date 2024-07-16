package ets.schedule.interfaces.services;

import com.auth0.jwt.interfaces.DecodedJWT;

import ets.schedule.data.payloads.login.LoginPayload;
import ets.schedule.data.responses.AuthResponse;

public interface AuthService {
    public AuthResponse login(LoginPayload payload);
    public DecodedJWT decodeToken(String token);
}
