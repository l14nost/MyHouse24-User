package lab.space.my_house_24_user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ThemeChangeController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ThemeChangeControllerTest {

    @MockBean
    private GlobalControllerAdvice globalControllerAdvice;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userService;

    @Test
    void getTheme() throws Exception {
        when(userService.getCurrentUser()).thenReturn(1L);
        when(userService.findById(1L)).thenReturn(User.builder().theme(true).build());
        mockMvc.perform(MockMvcRequestBuilders.get("/get-theme"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(true)));
    }

    @Test
    void getTheme_NotFound() throws Exception {
        when(userService.getCurrentUser()).thenReturn(1L);
        when(userService.findById(1L)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.get("/get-theme"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void changeTheme() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/change-theme")
                        .param("theme", "false"))
                .andExpect(status().isOk());
        verify(userService, times(1)).changeTheme(false);
    }
}