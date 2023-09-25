package lab.space.my_house_24_user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.model.summary.SummaryResponse;
import lab.space.my_house_24_user.service.MessageService;
import lab.space.my_house_24_user.service.SummaryService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SummaryController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class SummaryControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SummaryService summaryService;

    @Test
    void summaryPage() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/statistic/summary/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/statistic/statistic"));
    }

    @Test
    void getSummaryById() throws Exception {
        when(summaryService.summaryByApartment(1L)).thenReturn(SummaryResponse.builder().build());
        mockMvc.perform(MockMvcRequestBuilders.get("/statistic/get-summary-by-id/1"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(SummaryResponse.builder().build())));
    }

    @Test
    void getSummaryById_NotFound() throws Exception {
        when(summaryService.summaryByApartment(1L)).thenThrow(new EntityNotFoundException());
        mockMvc.perform(MockMvcRequestBuilders.get("/statistic/get-summary-by-id/1"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Apartment not found"));
    }
}