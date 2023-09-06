package lab.space.my_house_24_user.service;

import lab.space.my_house_24_user.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUsername(String token);

    String generateToken(UserDetails userDetails);

    boolean isTokenValid(String token, UserDetails userDetails, User user);
}
