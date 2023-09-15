package lab.space.my_house_24_user.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.enums.UserStatus;
import lab.space.my_house_24_user.mapper.UserMapper;
import lab.space.my_house_24_user.model.auth.ForgotPassRequest;
import lab.space.my_house_24_user.model.auth.ForgotRequest;
import lab.space.my_house_24_user.model.auth.RegisterRequest;
import lab.space.my_house_24_user.model.user.UserEditRequest;
import lab.space.my_house_24_user.model.user.UserResponseForEdit;
import lab.space.my_house_24_user.model.user.UserResponseForProfile;
import lab.space.my_house_24_user.model.user.UserResponseForSidebar;
import lab.space.my_house_24_user.repository.UserRepository;
import lab.space.my_house_24_user.service.JwtService;
import lab.space.my_house_24_user.service.UserService;
import lab.space.my_house_24_user.util.CustomMailSender;
import lab.space.my_house_24_user.util.FileHandler;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomMailSender customMailSender;

    private final String url = "http://localhost:8082/cabinet/";


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).orElseThrow(()->new EntityNotFoundException("User by email:"+username+" not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), user.getAuthorities());
    }
    @Override
    public User findUserByEmail(String email) {
        log.info("Try to find user by email: " + email);
        return userRepository.findUserByEmail(email).orElseThrow(()->new EntityNotFoundException("User by email:"+email+" not found"));
    }

    @Override
    public Long getCurrentUser() {
        log.info("Try to get current user");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByEmail(authentication.getName()).orElseThrow(() -> new EntityNotFoundException("User by email:" + authentication.getName() + " not found")).getId();
    }
    @Override
    public User findById(Long id) {
        log.info("Try to find user by id: "+ id);
        return userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User by id:"+id+" not found"));

    }

    @Override
    public void changeTheme(Boolean theme) {
        log.info("Try to change theme to: "+ theme);
        User user = findById(getCurrentUser());
        user.setTheme(theme);
        userRepository.save(user);
        log.info("Theme change");
    }

    @Override
    public UserResponseForSidebar getUserForSidebar() {
        log.info("Try to get sidebar dto from current user");
        return UserMapper.entityToDtoForSidebar(findById(getCurrentUser()));
    }

    @Override
    public UserResponseForEdit findUserForEdit() {
        log.info("Try to get edit dto from current user");
        return UserMapper.entityToEditDto(findById(getCurrentUser()));
    }

    @Override
    public UserResponseForProfile findUserForProfile() {
        log.info("Try to get profile dto from current user");
        return UserMapper.entityToProfileDto(findById(getCurrentUser()));
    }

    @Override
    public void update(UserEditRequest userEditRequest) {
        log.info("Try to update current user");
        User user = findById(userEditRequest.id());
        Boolean changeAuthenticate = false;
        String filenameDelete = user.getFilename();
        String oldEmail = user.getEmail();
        user.setDate(userEditRequest.date().atStartOfDay(ZoneId.systemDefault()).toInstant());
        user.setFirstname(userEditRequest.firstname());
        user.setLastname(userEditRequest.lastname());
        user.setSurname(userEditRequest.surname());
        user.setNumber(userEditRequest.number());
        user.setEmail(userEditRequest.email());
        user.setTelegram(userEditRequest.telegram());
        user.setViber(userEditRequest.viber());
        user.setNotes(userEditRequest.notes());
        if (!userEditRequest.img().isEmpty()) {
            user.setFilename(FileHandler.saveFile(userEditRequest.img()));
            FileHandler.deleteFile(filenameDelete);
        }
        if (!userEditRequest.password().isEmpty()){
            user.setPassword(new BCryptPasswordEncoder().encode(userEditRequest.password()));
            changeAuthenticate = true;
            log.info("Change password");
        }
        if (!oldEmail.equals(user.getEmail())){
            changeAuthenticate = true;
            log.info("Change email");
        }
        userRepository.save(user);
        log.info("Update user");
        if (changeAuthenticate){
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(),user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info("Reload security context");
        }

    }

    @Override
    public void register(RegisterRequest registerRequest) {
        log.info("Try to register new user");
        User user = User.builder()
                .password(passwordEncoder.encode(registerRequest.password()))
                .email(registerRequest.email())
                .duty(false)
                .addDate(Instant.now())
                .userStatus(UserStatus.NEW)
                .lastname(registerRequest.lastname())
                .firstname(registerRequest.firstname())
                .surname(registerRequest.surname())
                .build();

        userRepository.save(user);
        log.info("Register new user");
    }

    @Override
    public void sendForgotPasswordLetter(ForgotRequest forgotRequest) {
        log.info("Try to send forgot password link to user");
        User user = findUserByEmail(forgotRequest.email());
        String token = jwtService.generateToken(user);
        user.setToken(token);
        user.setTokenUsage(false);
        userRepository.save(user);
        customMailSender.send(user.getEmail(), url + "login/forgot-password/" + token, "Forgot Password");
        log.info("Letter was send");
    }

    @Override
    public UserDetails loadUserByToken(String token) {
        log.info("Try to find user by token");
        return loadUserByUsername(jwtService.extractUsername(token));
    }

    @Override
    public void forgotPassword(ForgotPassRequest forgotPassRequest, String token) {
        log.info("Try to change password");
        User user = findUserByEmail(loadUserByToken(token).getUsername());
        user.setTokenUsage(true);
        user.setPassword(passwordEncoder.encode(forgotPassRequest.password()));
        userRepository.save(user);
        log.info("Password was change");
        String textForSend = "Dear " + user.getLastname() + " " + user.getFirstname() + ", your password has been changed!\n" +
                "For detail information contact our support team";
        customMailSender.send(user.getEmail(), textForSend, "Password Change Notification");
        log.info("Letter about changing password was send");
    }

}
