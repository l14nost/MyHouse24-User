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
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
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

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomMailSender customMailSender;

    private final String url = "http://localhost:8082/cabinet/";


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(username).orElseThrow(()->new EntityNotFoundException("User by email:"+username+" not found"));
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(()->new EntityNotFoundException("User by email:"+email+" not found"));
    }

    @Override
    public Long getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findUserByEmail(authentication.getName()).orElseThrow(() -> new EntityNotFoundException("User by email:"+authentication.getName()+" not found")).getId();

    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new EntityNotFoundException("User by id:"+id+" not found"));

    }

    @Override
    public void changeTheme(Boolean theme) {
        User user = findById(getCurrentUser());
        user.setTheme(theme);
        userRepository.save(user);
    }

    @Override
    public UserResponseForSidebar getUserForSidebar() {
        return UserMapper.entityToDtoForSidebar(findById(getCurrentUser()));
    }

    @Override
    public UserResponseForEdit findUserForEdit() {
        return UserMapper.entityToEditDto(findById(getCurrentUser()));
    }

    @Override
    public UserResponseForProfile findUserForProfile() {
        return UserMapper.entityToProfileDto(findById(getCurrentUser()));
    }

    @Override
    public void update(UserEditRequest userEditRequest) {
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
        }
        if (!oldEmail.equals(user.getEmail())){
            changeAuthenticate = true;
        }
        userRepository.save(user);
        if (changeAuthenticate){
            Authentication authentication = new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword(),new ArrayList<>());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

    }

    @Override
    public void register(RegisterRequest registerRequest) {
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
    }

    @Override
    public void sendForgotPasswordLetter(ForgotRequest forgotRequest) {
        User user = findUserByEmail(forgotRequest.email());
        String token = jwtService.generateToken(user);
        user.setForgotToken(token);
        user.setForgotTokenUsage(false);
        userRepository.save(user);
        customMailSender.send(user.getEmail(), url + "login/forgot-password/" + token, "Forgot Password");
    }

    @Override
    public UserDetails loadUserByToken(String token) {
        return loadUserByUsername(jwtService.extractUsername(token));
    }

    @Override
    public void forgotPassword(ForgotPassRequest forgotPassRequest, String token) {
        User user = findUserByEmail(loadUserByToken(token).getUsername());
        user.setForgotTokenUsage(true);
        user.setPassword(passwordEncoder.encode(forgotPassRequest.password()));
        userRepository.save(user);
        String textForSend = "Dear " + user.getLastname() + " " + user.getFirstname() + ", your password has been changed!\n" +
                "For detail information contact our support team";
        customMailSender.send(user.getEmail(), textForSend, "Password Change Notification");

    }

}
