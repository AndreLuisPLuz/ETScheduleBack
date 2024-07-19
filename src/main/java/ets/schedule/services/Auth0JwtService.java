package ets.schedule.services;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;
import java.util.stream.Collectors;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.HttpEntity;
import ets.schedule.data.payloads.login.LoginConfirmPayload;
import ets.schedule.data.payloads.login.LoginPayload;
import ets.schedule.data.responses.login.AuthConfirmResponse;
import ets.schedule.data.responses.login.AuthResponse;
import ets.schedule.data.responses.profile.ProfileResponse;
import ets.schedule.enums.ProfileRole;
import ets.schedule.interfaces.services.AuthService;
import ets.schedule.interfaces.services.PasswordService;
import ets.schedule.repositories.ProfileJPARepository;
import ets.schedule.repositories.UserJPARepository;

public class Auth0JwtService implements AuthService {
    @Autowired
    UserJPARepository userRepo;

    @Autowired
    ProfileJPARepository profileRepo;

    @Autowired
    PasswordService passwordService;

    static private KeyPair keyPair;

    static private void generateRSAKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);

            keyPair = generator.generateKeyPair();
        } catch (NoSuchAlgorithmException ex) {
            throw new ApplicationException(500, "Key specification incorrectly set in server.");
        }
    }

    @Override
    public HttpEntity<AuthConfirmResponse> checkAsync(
            LoginConfirmPayload payload) {
        var matchingUsers = userRepo.findByUsername(payload.username());
        if (matchingUsers.size() == 0)
            throw new ApplicationException(404, "User not found.");

        var user = matchingUsers.get(0);
        var passwordsMatch = passwordService.matchPasswords(
                payload.password(),
                user.getPassword());
                
        if (!passwordsMatch)
            throw new ApplicationException(400, "Passwords do not match.");

        List<ProfileResponse> profileData = user.getProfiles()
                .stream()
                .map(p -> ProfileResponse.buildFromEntity(p))
                .collect(Collectors.toList());

        var response = new AuthConfirmResponse(true, profileData);

        return new HttpEntity<AuthConfirmResponse>(
                HttpStatusCode.valueOf(200),
                response);
    }

    @Override
    public HttpEntity<AuthResponse> loginAsync(LoginPayload payload) {
        if (keyPair == null) {
            generateRSAKeyPair();
        }

        var matchingUsers = userRepo.findByUsername(payload.username());
        if (matchingUsers.size() == 0)
            throw new ApplicationException(404, "User not found.");

        var user = matchingUsers.get(0);
        var passwordsMatch = passwordService.matchPasswords(
                payload.password(),
                user.getPassword());
        
                
        if (!passwordsMatch)
            throw new ApplicationException(400, "Passwords do not match.");

        ProfileRole profileRole;
        try {
            profileRole = ProfileRole.getRole(payload.role());
        } catch (IllegalArgumentException ex) {
            throw new ApplicationException(400, "Invalid user role.");
        }
        
        var profiles = profileRepo.findByUser(user)
            .stream()
            .filter(p -> p.getRole().equals(profileRole))
            .collect(Collectors.toList());

        if (profiles.size() == 0)
            throw new ApplicationException(400, "Role not atributed to user.");

        var publicKey = (RSAPublicKey) keyPair.getPublic();
        var privateKey = (RSAPrivateKey) keyPair.getPrivate();

        String token;
        try {
            Algorithm algorithm = Algorithm.RSA512(publicKey, privateKey);

            token = JWT.create()
                    .withIssuer("Andrezinho")
                    .withClaim("userId", user.getId().toString())
                    .withClaim("profileId", profiles.get(0).getId().toString())
                    .withClaim("role", profileRole.getRole())
                    .withExpiresAt(Instant.now().plusSeconds(28800))
                    .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new ApplicationException(500, "Claims couldn't be converted.");
        }

        var response = new AuthResponse(
            "Successful login.",
            token,
            user.getId()
        );

        return new HttpEntity<AuthResponse>(
                HttpStatusCode.valueOf(200),
                response);
    }

    @Override
    public DecodedJWT decodeTokenAsync(String auth) {
        if (keyPair == null) {
            generateRSAKeyPair();
        }

        var token = auth.split(" ")[1];

        var publicKey = (RSAPublicKey) keyPair.getPublic();
        var privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Algorithm algorithm = Algorithm.RSA512(publicKey, privateKey);
        Verification verification = JWT.require(algorithm)
                .withIssuer("Andrezinho");

        try {
            JWTVerifier verifier = verification.build();
            return verifier.verify(token);
        } catch (JWTVerificationException ex) {
            throw new ApplicationException(403, "Invalid token.");
        }
    }
}
