package lab.space.my_house_24_user.service;

import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.model.user.UserResponseForSidebar;

import java.util.Optional;

public interface UserService {
    User findUserByEmail(String email);
    Long getCurrentUser();

    User findById(Long id);

    void changeTheme(Boolean theme);

    UserResponseForSidebar getUserForSidebar();
}
