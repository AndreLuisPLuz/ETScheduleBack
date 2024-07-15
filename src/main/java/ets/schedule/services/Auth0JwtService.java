package ets.schedule.services;

import com.auth0.jwt.interfaces.DecodedJWT;

import ets.schedule.data.payloads.LoginPayload;
import ets.schedule.data.responses.AuthResponse;
import ets.schedule.interfaces.services.AuthService;

public class Auth0JwtService implements AuthService {
    @Override
    public AuthResponse login(LoginPayload payload) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public DecodedJWT decodeToken(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'decodeToken'");
    }
}
