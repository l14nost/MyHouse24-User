package lab.space.my_house_24_user.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.model.message.MessageMainPageRequest;
import lab.space.my_house_24_user.model.message.MessageResponseForCard;
import lab.space.my_house_24_user.model.message.MessageResponseForMain;
import lab.space.my_house_24_user.service.MessageService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MessageController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MessageControllerTest {
    @MockBean
    private GlobalControllerAdvice globalControllerAdvice;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private MessageService messageService;

    @Test
    void messagePage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/messages"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/message/message-main"));
    }

    @Test
    void getAllMessage() throws Exception {
        Page<MessageResponseForMain> messageResponseForMainPage = new PageImpl<>(List.of(
                MessageResponseForMain.builder().build(),
                MessageResponseForMain.builder().build(),
                MessageResponseForMain.builder().build(),
                MessageResponseForMain.builder().build()
        ));
        when(messageService.findAllForMessageMain(new MessageMainPageRequest(1,"key"))).thenReturn(messageResponseForMainPage);
        mockMvc.perform(MockMvcRequestBuilders.post("/messages/get-all-message")
                        .content(objectMapper.writeValueAsString(new MessageMainPageRequest(1,"key")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(messageResponseForMainPage)));
    }

    @Test
    void messageCardPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/messages/message-card/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/message/message-card"));
        verify(messageService, times(1)).changeCheck(1L);
    }

    @Test
    void getMessageById() throws Exception {
        when(messageService.findByIdForCard(1L)).thenReturn(MessageResponseForCard.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/messages/get-message-by-id/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(MessageResponseForCard.builder().build())));
    }

    @Test
    void getMessageById_NotFound() throws Exception {
        when(messageService.findByIdForCard(1L)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.get("/messages/get-message-by-id/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Message not found"));
    }

    @Test
    void getAllMessageForHeader() throws Exception {
        when(messageService.findAllForMessageHeader()).thenReturn(List.of());
        mockMvc.perform(MockMvcRequestBuilders.get("/messages/get-message-for-header"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(List.of())));
    }

    @Test
    void deleteMessage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/messages/delete-message/1"))
                .andExpect(status().isOk());
        verify(messageService,times(1)).deleteMessage(1L);
    }

    @Test
    void deleteMessages() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/messages/delete-messages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("ids","1,2,3"))
                .andExpect(status().isOk());
        verify(messageService,times(3)).deleteMessage(any(Long.class));
    }
}