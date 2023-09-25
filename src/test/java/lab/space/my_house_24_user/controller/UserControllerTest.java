package lab.space.my_house_24_user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.model.user.UserResponseForEdit;
import lab.space.my_house_24_user.model.user.UserResponseForProfile;
import lab.space.my_house_24_user.model.user.UserResponseForSidebar;
import lab.space.my_house_24_user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @MockBean
    private GlobalControllerAdvice globalControllerAdvice;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void getCurrentUser() throws Exception {
        when(userService.getUserForSidebar()).thenReturn(UserResponseForSidebar.builder()
                        .id(1L)
                .build());

        mockMvc.perform(get("/user/get-current-user"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(UserResponseForSidebar.builder().id(1L).build())));
    }

    @Test
    void getCurrentUser_NotFound() throws Exception {
        when(userService.getUserForSidebar()).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/user/get-current-user"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));
    }


    @Test
    void getUserProfile() throws Exception {
        when(userService.findUserForProfile()).thenReturn(UserResponseForProfile.builder()
                .id(1L)
                .build());

        mockMvc.perform(get("/user/get-user-profile"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(UserResponseForProfile.builder().id(1L).build())));

    }

    @Test
    void getUserProfile_NotFound() throws Exception {
        when(userService.findUserForProfile()).thenThrow(new EntityNotFoundException());

        mockMvc.perform(get("/user/get-user-profile"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("User not found"));

    }
}