package lab.space.my_house_24_user.service.impl;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtServiceImpl implements JwtService {

    @Value("${app.jwt.secret}")
    private String JWT_SECRET;
    private final int JWT_EXPIRED_TIME = 15;

    @Override
    public String extractUsername(String token) {
        return JWT.decode(token).getSubject();
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 60000L * JWT_EXPIRED_TIME))
                .sign(getSignInAlgorithm());
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails, User user) {
        try {
            JWT.require(getSignInAlgorithm()).withSubject(userDetails.getUsername()).build().verify(token);
            if (user.getToken().equals(token)) {
                return !user.getTokenUsage();
            }
            return false;

        } catch (JWTVerificationException e) {
            log.warn(e.getMessage());
            return false;
        }
    }


    private Algorithm getSignInAlgorithm() {
        return Algorithm.HMAC256(Base64.getDecoder().decode(JWT_SECRET));
    }
}
