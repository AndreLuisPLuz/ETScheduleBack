 package ets.schedule.interfaces.services;

 import com.auth0.jwt.interfaces.DecodedJWT;

 import ets.schedule.data.HttpEntity;
 import ets.schedule.data.payloads.login.LoginConfirmPayload;
 import ets.schedule.data.payloads.login.LoginPayload;
 import ets.schedule.data.responses.login.AuthConfirmResponse;
 import ets.schedule.data.responses.login.AuthResponse;

 public interface AuthService {
     HttpEntity<AuthConfirmResponse> checkAsync(LoginConfirmPayload payload);
     HttpEntity<AuthResponse> loginAsync(LoginPayload payload);
     DecodedJWT decodeTokenAsync(String token);
 }
