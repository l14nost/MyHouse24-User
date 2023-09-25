package lab.space.my_house_24_user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lab.space.my_house_24_user.model.user.UserEditRequest;
import lab.space.my_house_24_user.model.user.UserResponseForEdit;
import lab.space.my_house_24_user.model.user.UserResponseForSidebar;
import lab.space.my_house_24_user.service.UserService;
import lab.space.my_house_24_user.validator.UserValidator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProfileController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class ProfileControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserValidator userValidator;

    @Test
    void profileEditPage() throws Exception {
        when(userService.findUserForEdit()).thenReturn(UserResponseForEdit.builder().id(1L).build());

        mockMvc.perform(get("/profile/edit"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/profile/profile-edit"));
    }

    @Test
    void profileEdit() throws Exception {
        UserEditRequest userEditRequest = UserEditRequest.builder()
                .password("pass")
                .date(LocalDate.now())
                .notes("notes")
                .number("1231231231")
                .email("mail@gmail.com")
                .firstname("Name")
                .viber("1231231231")
                .telegram("1231231231")
                .img(new MockMultipartFile("file","example.jpg","text/plain","Hello World".getBytes()))
                .currentPassword("pass")
                .confirmPassword("pass")
                .lastname("Last")
                .surname("Sur")
                .build();

        mockMvc.perform(put("/profile/edit")
                        .flashAttr("userEditRequest",userEditRequest))
                .andExpect(status().isOk());
        verify(userService).update(any(UserEditRequest.class));
    }

    @Test
    void profileEdit_Valid() throws Exception {
        UserEditRequest userEditRequest = UserEditRequest.builder()
                .password("pass")
                .date(LocalDate.now())
                .notes("notes")
                .number("1231231231")
                .email("mail@gmail.com")
                .firstname("Name")
                .viber("1231231231")
                .telegram("1231231231")
                .img(new MockMultipartFile("file","example.txt","text/plain","Hello World".getBytes()))
                .currentPassword("pass")
                .confirmPassword("pass")
                .lastname("Last")
                .surname("Sur")
                .build();

        mockMvc.perform(put("/profile/edit")
                        .flashAttr("userEditRequest",userEditRequest))
                .andExpect(status().isBadRequest());
        verify(userService, times(0)).update(any(UserEditRequest.class));
    }

    @Test
    void profilePage() throws Exception {
        mockMvc.perform(get("/profile/"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/profile/profile-main"));
    }
}