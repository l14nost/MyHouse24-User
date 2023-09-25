package lab.space.my_house_24_user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.space.my_house_24_user.model.auth.RegisterRequest;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.validation.BindingResult;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RegisterController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class RegisterControllerTest {

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

    @Test
    void showLogin() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/auth/register"));
    }

    @Test
    void register() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .content(objectMapper.writeValueAsString(new RegisterRequest("Lastname", "Firstname", "Surname", "mail@gmail.com","pass","pass",true)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(userValidator, times(1)).passwordMatch(anyString(),anyString(),any(BindingResult.class),anyString());
        verify(userValidator, times(1)).uniqueEmail(anyString(),anyLong(),any(BindingResult.class),anyString());
        verify(userValidator, times(1)).checkConfirm(anyBoolean(),any(BindingResult.class),anyString());
        verify(userService, times(1)).register(new RegisterRequest("Lastname", "Firstname", "Surname", "mail@gmail.com","pass","pass",true));
    }

    @Test
    void register_Valid() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/register")
                        .content(objectMapper.writeValueAsString(new RegisterRequest("Lastname", "Firstname", "Surname", "mail","pass","pass",true)))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        verify(userValidator, times(1)).passwordMatch(anyString(),anyString(),any(BindingResult.class),anyString());
        verify(userValidator, times(1)).uniqueEmail(anyString(),anyLong(),any(BindingResult.class),anyString());
        verify(userValidator, times(1)).checkConfirm(anyBoolean(),any(BindingResult.class),anyString());
        verify(userService, times(0)).register(new RegisterRequest("Lastname", "Firstname", "Surname", "mail@gmail.com","pass","pass",true));
    }

    @Test
    void showPrivacy() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/privacy-policy"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/auth/privacy-policy"));
    }
}