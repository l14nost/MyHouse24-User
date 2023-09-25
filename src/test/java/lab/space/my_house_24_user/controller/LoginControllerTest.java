package lab.space.my_house_24_user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.enums.UserStatus;
import lab.space.my_house_24_user.model.auth.ForgotPassRequest;
import lab.space.my_house_24_user.model.auth.ForgotRequest;
import lab.space.my_house_24_user.service.JwtService;
import lab.space.my_house_24_user.service.UserService;
import lab.space.my_house_24_user.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(LoginController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class LoginControllerTest {

    @MockBean
    private GlobalControllerAdvice globalControllerAdvice;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserValidator userValidator;
    @MockBean
    private UserService userService;
    @MockBean
    private JwtService jwtService;


    @Test
    void showLogin_Redirect() throws Exception {
        User user = User.builder().password("pass").email("mail@gmail.com").build();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserStatus.ACTIVE.name());
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
        Authentication authentication = new UsernamePasswordAuthenticationToken(
               userDetails,null,Collections.singletonList(authority));
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(
                authentication
        );
        SecurityContextHolder.setContext(securityContext);

        mockMvc.perform(get("/login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/index"));
    }

    @Test
    void showLogin() throws Exception {
        User user = User.builder().password("pass").email("mail@gmail.com").build();

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(UserStatus.ACTIVE.name());
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword(),
                Collections.singletonList(authority)
        );
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(
                authentication
        );
        SecurityContextHolder.setContext(securityContext);
        mockMvc.perform(get("/login"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/auth/login"));
    }

    @Test
    void showForgot() throws Exception {
        mockMvc.perform(get("/login/forgot-password-send"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/auth/forgot-password"));
    }



    @Test
    void sendForgot() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login/forgot-password-send")
                        .content(objectMapper.writeValueAsString(new ForgotRequest("mail@gmail.com")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userValidator, times(1)).checkStatus(any(String.class), any(BindingResult.class), any(String.class));
        verify(userValidator, times(1)).existByEmail(any(String.class), any(BindingResult.class), any(String.class));
        verify(userService, times(1)).sendForgotPasswordLetter(new ForgotRequest("mail@gmail.com"));
    }

    @Test
    void sendForgot_Valid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/login/forgot-password-send")
                        .content(objectMapper.writeValueAsString(new ForgotRequest("mail")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void showForgotPasswordChangePage() throws Exception {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username("mail@test.gmail")
                .password("pass")
                .authorities(List.of(new SimpleGrantedAuthority(UserStatus.ACTIVE.name())))
                .build();
        User user = User.builder().email("mail@test.gmail")
                .password("pass")
                .userStatus(UserStatus.ACTIVE).build();
        when(userService.loadUserByToken("token")).thenReturn(userDetails);
        when(userService.findUserByEmail("mail@test.gmail")).thenReturn(user);
        when(jwtService.isTokenValid("token",userDetails,user)).thenReturn(true);
        mockMvc.perform(get("/login/forgot-password/token"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/auth/new-password"));
    }

    @Test
    void showForgotPasswordChangePage_BadRequest() throws Exception {
        UserDetails userDetails = org.springframework.security.core.userdetails.User.builder()
                .username("mail@test.gmail")
                .password("pass")
                .authorities(List.of(new SimpleGrantedAuthority(UserStatus.ACTIVE.name())))
                .build();
        User user = User.builder().email("mail@test.gmail")
                .password("pass")
                .userStatus(UserStatus.ACTIVE).build();
        when(userService.loadUserByToken("token")).thenReturn(userDetails);
        when(userService.findUserByEmail("mail@test.gmail")).thenReturn(user);
        when(jwtService.isTokenValid("token",userDetails,user)).thenReturn(false);
        mockMvc.perform(get("/login/forgot-password/token"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/auth/new-password-error"));
    }

    @Test
    void forgotPasswordStaff() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/login/forgot-password/token")
                        .content(objectMapper.writeValueAsString(new ForgotPassRequest("pass","pass")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userValidator, times(1)).passwordMatch(any(String.class), any(String.class), any(BindingResult.class), any(String.class));
        verify(userService, times(1)).forgotPassword(new ForgotPassRequest("pass","pass"),"token");
    }

    @Test
    void forgotPasswordStaff_Valid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/login/forgot-password/token")
                        .content(objectMapper.writeValueAsString(new ForgotPassRequest("","")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(userValidator, times(1)).passwordMatch(any(String.class), any(String.class), any(BindingResult.class), any(String.class));
        verify(userService, times(0)).forgotPassword(new ForgotPassRequest("pass","pass"),"token");
    }
}