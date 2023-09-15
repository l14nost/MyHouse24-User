package lab.space.my_house_24_user.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(ErrorController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ErrorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void handle404() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/error-404"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/pages/error/404"));
    }

    @Test
    void accessDenied() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/access-denied"))
                .andExpect(status().isOk())
                .andExpect(view().name("/user/pages/error/403"));
    }
}