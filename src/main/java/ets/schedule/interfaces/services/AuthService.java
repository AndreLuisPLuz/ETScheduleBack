package ets.schedule.interfaces.services;

import java.util.concurrent.CompletableFuture;

import com.auth0.jwt.interfaces.DecodedJWT;

import ets.schedule.data.payloads.login.LoginConfirmPayload;
import ets.schedule.data.payloads.login.LoginPayload;
import ets.schedule.data.responses.login.AuthConfirmResponse;
import ets.schedule.data.responses.login.AuthResponse;

public interface AuthService {
    CompletableFuture<AuthConfirmResponse> checkAsync(LoginConfirmPayload payload);
    CompletableFuture<AuthResponse> loginAsync(LoginPayload payload);
    CompletableFuture<DecodedJWT> decodeTokenAsync(String token);
}
