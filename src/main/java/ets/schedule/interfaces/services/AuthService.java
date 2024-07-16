package ets.schedule.interfaces.services;

import java.util.concurrent.CompletableFuture;

import com.auth0.jwt.interfaces.DecodedJWT;

import ets.schedule.data.payloads.login.LoginConfirmPayload;
import ets.schedule.data.payloads.login.LoginPayload;
import ets.schedule.data.responses.AuthResponse;

public interface AuthService {
    CompletableFuture<Boolean> check(LoginConfirmPayload payload);
    CompletableFuture<AuthResponse> login(LoginPayload payload);
    CompletableFuture<DecodedJWT> decodeToken(String token);
}
