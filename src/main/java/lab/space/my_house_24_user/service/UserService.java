package lab.space.my_house_24_user.service;

import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.model.auth.ForgotPassRequest;
import lab.space.my_house_24_user.model.auth.ForgotRequest;
import lab.space.my_house_24_user.model.auth.RegisterRequest;
import lab.space.my_house_24_user.model.user.UserEditRequest;
import lab.space.my_house_24_user.model.user.UserResponseForEdit;
import lab.space.my_house_24_user.model.user.UserResponseForProfile;
import lab.space.my_house_24_user.model.user.UserResponseForSidebar;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserService {

    User findUserByEmail(String email);
    Long getCurrentUser();

    User findById(Long id);

    void changeTheme(Boolean theme);

    UserResponseForSidebar getUserForSidebar();

    UserResponseForEdit findUserForEdit();
    UserResponseForProfile findUserForProfile();

    void update(UserEditRequest userEditRequest);

    void register(RegisterRequest registerRequest);

    void sendForgotPasswordLetter(ForgotRequest forgotRequest);

    UserDetails loadUserByToken(String token);

    void forgotPassword(ForgotPassRequest forgotPassRequest, String token);
}
