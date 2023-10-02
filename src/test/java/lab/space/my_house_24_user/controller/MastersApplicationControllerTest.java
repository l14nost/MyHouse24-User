package lab.space.my_house_24_user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.enums.Master;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForSidebar;
import lab.space.my_house_24_user.model.enums_response.EnumResponse;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationRequest;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationResponse;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationSaveRequest;
import lab.space.my_house_24_user.service.ApartmentService;
import lab.space.my_house_24_user.service.MastersApplicationService;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MastersApplicationController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class MastersApplicationControllerTest {

    @MockBean
    private MastersApplicationService mastersApplicationService;

    @MockBean
    private ApartmentService apartmentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void showMastersApplicationPage() throws Exception {
        mockMvc.perform(get("/masters-applications"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/masters_application/masters-application"));
    }

    @Test
    void showMastersApplicationSavePage() throws Exception {
        mockMvc.perform(get("/masters-applications/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/masters_application/masters-application-save"));
    }

    @Test
    void getAllTypeMaster() throws Exception {
        List<EnumResponse> enumResponses = List.of(
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build()
        );
        when(mastersApplicationService.getAllTypeMaster()).thenReturn(enumResponses);
        mockMvc.perform(get("/masters-applications/get-all-type-master"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(enumResponses)));
    }

    @Test
    void getAllApartment() throws Exception {
        List<ApartmentResponseForSidebar> apartmentResponseForSidebars = List.of(
                ApartmentResponseForSidebar.builder().build(),
                ApartmentResponseForSidebar.builder().build(),
                ApartmentResponseForSidebar.builder().build(),
                ApartmentResponseForSidebar.builder().build()
        );
        when(apartmentService.getAllApartmentResponse()).thenReturn(apartmentResponseForSidebars);
        mockMvc.perform(get("/masters-applications/get-all-apartment"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(apartmentResponseForSidebars)));
    }

    @Test
    void getAllMastersApplication() throws Exception {
        Page<MastersApplicationResponse> mastersApplicationResponses = new PageImpl<>(List.of(
                MastersApplicationResponse.builder().build(),
                MastersApplicationResponse.builder().build(),
                MastersApplicationResponse.builder().build(),
                MastersApplicationResponse.builder().build()
        ));
        when(mastersApplicationService.getAllMastersApplicationByRequest(MastersApplicationRequest.builder().pageIndex(1).build())).thenReturn(mastersApplicationResponses);
        mockMvc.perform(post("/masters-applications/get-all-master-call")
                        .content(objectMapper.writeValueAsString(MastersApplicationRequest.builder().pageIndex(1).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(mastersApplicationResponses)));
    }

    @Test
    void saveMastersApplication() throws Exception {
        MastersApplicationSaveRequest validRequest = MastersApplicationSaveRequest.builder()
                .masterType(Master.ANY_MASTER)
                .apartmentId(1L)
                .date(LocalDate.now())
                .time(LocalTime.now())
                .description("Text")
                .build();
        mockMvc.perform(post("/masters-applications/save-master-call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(validRequest)))
                .andExpect(status().isOk());
    }

    @Test
    void saveMastersApplicationWithBadRequest() throws Exception {
        MastersApplicationSaveRequest invalidRequest = MastersApplicationSaveRequest.builder()
                .masterType(null)
                .apartmentId(0L)
                .date(null)
                .time(null)
                .description("")
                .build();

        mockMvc.perform(post("/masters-applications/save-master-call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void saveMastersApplicationWithEntityNotFoundExceptionRequest() throws Exception {
        MastersApplicationSaveRequest invalidRequest = MastersApplicationSaveRequest.builder()
                .masterType(Master.ANY_MASTER)
                .apartmentId(1L)
                .date(LocalDate.now())
                .time(LocalTime.now())
                .description("Text")
                .build();
        doThrow(new EntityNotFoundException("Not Found"))
                .when(mastersApplicationService)
                .saveMastersApplicationByRequest(invalidRequest);
        mockMvc.perform(post("/masters-applications/save-master-call")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMastersApplicationById() throws Exception {
        mockMvc.perform(delete("/masters-applications/delete-master-call/1"))
                .andExpect(status().isOk());
    }

    @Test
    void deleteMastersApplicationByIdWithBadRequestId() throws Exception {
        mockMvc.perform(delete("/masters-applications/delete-master-call/0"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void deleteMastersApplicationByIdWithEntityNotFoundExceptionRequestId() throws Exception {
        doThrow(new EntityNotFoundException("Not Found"))
                .when(mastersApplicationService)
                .deleteMastersApplicationById(anyLong());
        mockMvc.perform(delete("/masters-applications/delete-master-call/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteMastersApplicationByIdWithIllegalArgumentExceptionRequestId() throws Exception {
        doThrow(new IllegalArgumentException("Illegal Argument Exception"))
                .when(mastersApplicationService)
                .deleteMastersApplicationById(anyLong());
        mockMvc.perform(delete("/masters-applications/delete-master-call/1"))
                .andExpect(status().isBadRequest());
    }
}