package ets.schedule.services;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;

import ets.schedule.Exceptions.ApplicationException;
import ets.schedule.data.payloads.LoginPayload;
import ets.schedule.data.responses.AuthResponse;
import ets.schedule.interfaces.services.AuthService;
import ets.schedule.interfaces.services.PasswordService;
import ets.schedule.repositories.UserJPARepository;

public class Auth0JwtService implements AuthService {
    @Autowired
    UserJPARepository userRepo;

    @Autowired
    PasswordService passwordService;

    static private KeyPair keyPair;

    static private void generateRSAKeyPair() {
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            generator.initialize(2048);
    
            keyPair = generator.generateKeyPair();
        } catch(NoSuchAlgorithmException ex) {
            throw new ApplicationException(500, "Key specification incorrectly set in server.");
        }
    }

    @Override
    public AuthResponse login(LoginPayload payload) {
        if (keyPair == null) {
            generateRSAKeyPair();
        }

        var matchingUsers = userRepo.findByUsername(payload.username());
        if (matchingUsers.size() == 0)
            throw new ApplicationException(404, "User not found.");

        var user = matchingUsers.get(0);
        var passwordsMatch = passwordService.matchPasswords(
            payload.password(), 
            user.getPassword()
        );

        if (passwordsMatch)
            throw new ApplicationException(400, "Passwords do not match.");

        var publicKey = (RSAPublicKey) keyPair.getPublic();
        var privateKey = (RSAPrivateKey) keyPair.getPrivate();

        String token;
        try {
            Algorithm algorithm = Algorithm.RSA512(publicKey, privateKey);
            token = JWT.create()
                .withIssuer("Andrezinho")
                .withClaim("userId", user.getId().toString())
                .withExpiresAt(Instant.now().plusSeconds(28800))
                .sign(algorithm);
        } catch (JWTCreationException ex) {
            throw new ApplicationException(500, "Claims couldn't be converted.");
        }

        return new AuthResponse("Successful login.", token);
    }

    @Override
    public DecodedJWT decodeToken(String token) {
        if (keyPair == null) {
            generateRSAKeyPair();
        }

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
