package lab.space.my_house_24_user.service.impl;

import lab.space.my_house_24_user.entity.Apartment;
import lab.space.my_house_24_user.entity.House;
import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.enums.UserStatus;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForSidebar;
import lab.space.my_house_24_user.model.auth.ForgotPassRequest;
import lab.space.my_house_24_user.model.auth.ForgotRequest;
import lab.space.my_house_24_user.model.auth.RegisterRequest;
import lab.space.my_house_24_user.model.user.UserEditRequest;
import lab.space.my_house_24_user.model.user.UserResponseForEdit;
import lab.space.my_house_24_user.model.user.UserResponseForProfile;
import lab.space.my_house_24_user.model.user.UserResponseForSidebar;
import lab.space.my_house_24_user.repository.UserRepository;
import lab.space.my_house_24_user.service.JwtService;
import lab.space.my_house_24_user.util.CustomMailSender;
import lab.space.my_house_24_user.util.FileHandler;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private JwtService jwtService;
    @Mock
    private CustomMailSender customMailSender;
    @Mock
    private SecurityContext securityContext;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void loadUserByUsername() {
        when(userRepository.findUserByEmail("mail@test.gmail")).thenReturn(Optional.of(
                User.builder().email("mail@test.gmail")
                        .password("pass")
                        .userStatus(UserStatus.ACTIVE).build()
        ));
        UserDetails userDetails = userService.loadUserByUsername("mail@test.gmail");
        UserDetails userDetails1 = org.springframework.security.core.userdetails.User.builder()
                .username("mail@test.gmail")
                .password("pass")
                .authorities(List.of(new SimpleGrantedAuthority(UserStatus.ACTIVE.name())))
                .build();
        assertEquals(userDetails1,userDetails);
    }

    @Test
    void findUserByEmail() {
        when(userRepository.findUserByEmail("mail@test.gmail")).thenReturn(Optional.of(
                User.builder().email("mail@test.gmail")
                        .password("pass")
                        .userStatus(UserStatus.ACTIVE).build()
        ));
        User user = userService.findUserByEmail("mail@test.gmail");
        assertEquals( User.builder().email("mail@test.gmail")
                .password("pass")
                .userStatus(UserStatus.ACTIVE).build(),user);
    }

    @Test
    void getCurrentUser() {
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("mail@gmail.com");
        when(userRepository.findUserByEmail("mail@gmail.com")).thenReturn(Optional.of( User.builder()
                .id(1L).build()));
        assertEquals(1L,userService.getCurrentUser());

    }

    @Test
    void findById() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(
                User.builder()
                        .id(1L)
                        .email("mail@test.gmail")
                        .password("pass")
                        .userStatus(UserStatus.ACTIVE).build()
        ));
        assertEquals( User.builder()
                .id(1L)
                .email("mail@test.gmail")
                .password("pass")
                .userStatus(UserStatus.ACTIVE).build(),userService.findById(1L));

    }

    @Test
    void changeTheme() {
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("mail@gmail.com");
        when(userRepository.findUserByEmail("mail@gmail.com")).thenReturn(Optional.of( User.builder()
                .id(1L).build()));
        when(userRepository.findById(1L)).thenReturn(Optional.of(
                User.builder()
                        .id(1L)
                        .email("mail@test.gmail")
                        .password("pass")
                        .theme(false)
                        .userStatus(UserStatus.ACTIVE).build()
        ));
        userService.changeTheme(true);
        verify(userRepository).save( User.builder()
                .id(1L)
                .email("mail@test.gmail")
                .password("pass")
                .theme(true)
                .userStatus(UserStatus.ACTIVE).build());
    }

    @Test
    void getUserForSidebar() {
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("mail@gmail.com");
        when(userRepository.findUserByEmail("mail@gmail.com")).thenReturn(Optional.of( User.builder()
                .id(1L).build()));
        when(userRepository.findById(1L)).thenReturn(Optional.of(
                User.builder()
                        .id(1L)
                        .surname("surname")
                        .firstname("name")
                        .lastname("lastname")
                        .apartmentList(List.of(
                                Apartment.builder().id(1L)
                                        .house(House.builder().name("name").build())
                                        .number(1000).build()
                        ))
                        .filename("filename")
                        .build()
        ));
        UserResponseForSidebar userResponseForSidebar =
                UserResponseForSidebar.builder()
                        .id(1L)
                        .fullName("lastname name surname")
                        .apartments(List.of(
                                ApartmentResponseForSidebar.builder().id(1L)
                                        .fullName("name, №1000").build()
                        ))
                        .filename("filename")
                        .build();
        assertEquals(
                userResponseForSidebar,
                userService.getUserForSidebar()
        );
    }

    @Test
    void findUserForEdit() {
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("mail@gmail.com");
        when(userRepository.findUserByEmail("mail@gmail.com")).thenReturn(Optional.of( User.builder()
                .id(1L).build()));
        when(userRepository.findById(1L)).thenReturn(Optional.of(
                User.builder()
                        .id(1L)
                        .build()
        ));
        UserResponseForEdit userResponseForEdit =  UserResponseForEdit.builder()
                .id(1L)
                .build();
        assertEquals(userResponseForEdit, userService.findUserForEdit());
    }

    @Test
    void findUserForProfile() {
        Authentication authentication = mock(Authentication.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);
        when(authentication.getName()).thenReturn("mail@gmail.com");
        when(userRepository.findUserByEmail("mail@gmail.com")).thenReturn(Optional.of( User.builder()
                .id(1L)
                .apartmentList(List.of())
                .build()));
        when(userRepository.findById(1L)).thenReturn(Optional.of(
                User.builder()
                        .id(1L)
                        .apartmentList(List.of())
                        .build()
        ));
        assertEquals(UserResponseForProfile.builder().fullName("null null null").id(1L).apartments(List.of()).build(),userService.findUserForProfile());
    }

    @Test
    void update() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(
                User.builder()
                        .id(1L)
                        .surname("surname")
                        .firstname("name")
                        .lastname("lastname")
                        .number("123123123")
                        .viber("123123123")
                        .telegram("123123123")
                        .email("email@gmail.com")
                        .notes("notes")
                        .filename("filename")
                        .userStatus(UserStatus.ACTIVE)
                        .build()
        ));
        userService.update(UserEditRequest.builder()
                .id(1L)
                .surname("surname1")
                .firstname("name1")
                .lastname("lastname1")
                .number("1231231231")
                .viber("1231231231")
                .telegram("1231231231")
                .email("email@gmail.com1")
                        .password("pass")
                .notes("notes1")
                        .date(LocalDate.of(2021, Month.APRIL,12))
                .img(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                .build());

        verify(userRepository).save(any(User.class));



    }

    @Test
    void register() {
        RegisterRequest registerRequest = new RegisterRequest("lastname", "firstname", "surname", "email", "password", "confirmPass",true);
        userService.register(registerRequest);
        verify(userRepository).save(any(User.class));
    }

    @Test
    void sendForgotPasswordLetter() {

        when(userRepository.findUserByEmail("mail@test.gmail")).thenReturn(Optional.of(
                User.builder().email("mail@test.gmail")
                        .password("pass")
                        .tokenUsage(false)
                        .userStatus(UserStatus.ACTIVE).build()
        ));
        when(jwtService.generateToken(any(User.class))).thenReturn("token");
        userService.sendForgotPasswordLetter(new ForgotRequest("mail@test.gmail"));
        verify(userRepository).save(User.builder().email("mail@test.gmail")
                .password("pass")
                .tokenUsage(false)
                        .token("token")
                .userStatus(UserStatus.ACTIVE)
                .password("pass")
                .build()
        );
    }

    @Test
    void loadUserByToken() {
        when(jwtService.extractUsername("token")).thenReturn("mail@test.gmail");
        when(userRepository.findUserByEmail("mail@test.gmail")).thenReturn(Optional.of(
                User.builder().email("mail@test.gmail")
                        .password("pass")
                        .userStatus(UserStatus.ACTIVE).build()
        ));
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username("mail@test.gmail")
                .password("pass")
                .authorities(List.of(new SimpleGrantedAuthority(UserStatus.ACTIVE.name())))
                .build();
        assertEquals(userDetails, userService.loadUserByToken("token"));
    }

    @Test
    void forgotPassword() {
        when(jwtService.extractUsername("token")).thenReturn("mail@test.gmail");
        when(userRepository.findUserByEmail("mail@test.gmail")).thenReturn(Optional.of(
                User.builder().email("mail@test.gmail")
                        .password("pass")
                        .tokenUsage(false)
                        .userStatus(UserStatus.ACTIVE).build()
        ));
        when(passwordEncoder.encode("pass")).thenReturn("pass");
        userService.forgotPassword(ForgotPassRequest.builder()
                        .password("pass")
                        .confirmPassword("pass")
                .build(), "token");
        verify(userRepository).save(User.builder().email("mail@test.gmail")
                .password("pass")
                .tokenUsage(true)
                .userStatus(UserStatus.ACTIVE)
                        .password("pass")
                .build()
        );



    }
}