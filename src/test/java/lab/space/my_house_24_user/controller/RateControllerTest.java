package lab.space.my_house_24_user.controller;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForRate;
import lab.space.my_house_24_user.service.ApartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(RateController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class RateControllerTest {
    @MockBean
    private ApartmentService apartmentService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetRateWithValidApartmentId() throws Exception {
        ApartmentResponseForRate apartmentResponse = ApartmentResponseForRate.builder().build();
        when(apartmentService.getApartmentResponseForRateById(anyLong())).thenReturn(apartmentResponse);

        mockMvc.perform(get("/get-rate-by-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetRateWithInvalidApartmentId() throws Exception {
        when(apartmentService.getApartmentResponseForRateById(anyLong())).thenThrow(new EntityNotFoundException("Not found"));
        mockMvc.perform(get("/get-rate-by-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void showRate() throws Exception {
        mockMvc.perform(get("/rate-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/rate/rate"));
    }
}
