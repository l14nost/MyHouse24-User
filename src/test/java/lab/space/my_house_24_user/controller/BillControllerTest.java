package lab.space.my_house_24_user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForSidebar;
import lab.space.my_house_24_user.model.bill.BillRequest;
import lab.space.my_house_24_user.model.bill.BillResponse;
import lab.space.my_house_24_user.model.enums_response.EnumResponse;
import lab.space.my_house_24_user.service.ApartmentService;
import lab.space.my_house_24_user.service.BillService;
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

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BillController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
class BillControllerTest {

    @MockBean
    private BillService billService;

    @MockBean
    private ApartmentService apartmentService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void showAllBillsPage() throws Exception {
        mockMvc.perform(get("/bills-all"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/bill/bills"));
    }

    @Test
    void showAllBillsByApartmentIdPage() throws Exception {
        mockMvc.perform(get("/bills-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/bill/bills"));
    }

    @Test
    void showBillByIdPage() throws Exception {
        mockMvc.perform(get("/bill-1"))
                .andExpect(status().isOk())
                .andExpect(view().name("user/pages/bill/bill-card"));
    }

    @Test
    void getAllBillStatus() throws Exception {
        List<EnumResponse> enumResponses = List.of(
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build(),
                EnumResponse.builder().build()
        );
        when(billService.getAllBillStatus()).thenReturn(enumResponses);
        mockMvc.perform(get("/get-all-bill-status"))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(enumResponses)));
    }

    @Test
    void testGetBillById() throws Exception {
        Page<BillResponse> billResponses = new PageImpl<>(List.of(
                BillResponse.builder().build(),
                BillResponse.builder().build(),
                BillResponse.builder().build(),
                BillResponse.builder().build()
        ));
        when(billService.getBillsResponseByRequest(BillRequest.builder().pageIndex(1).build())).thenReturn(billResponses);
        mockMvc.perform(MockMvcRequestBuilders.post("/get-bills")
                        .content(objectMapper.writeValueAsString(BillRequest.builder().pageIndex(1).build()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(objectMapper.writeValueAsString(billResponses)));
    }

    @Test
    void getBillById() throws Exception {
        BillResponse billResponse = BillResponse.builder().build();
        when(billService.getBillResponseById(anyLong())).thenReturn(billResponse);
        mockMvc.perform(get("/get-bill-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getBillByIdWithInvalidId() throws Exception {
        when(billService.getBillResponseById(anyLong())).thenThrow(new EntityNotFoundException("Not found"));
        mockMvc.perform(get("/get-bill-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getApartmentById() throws Exception {
        ApartmentResponseForSidebar apartmentResponseForSidebar = ApartmentResponseForSidebar.builder().build();
        when(apartmentService.getApartmentResponseForSidebarById(anyLong())).thenReturn(apartmentResponseForSidebar);
        mockMvc.perform(get("/get-apartment-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void getApartmentByIdWithInvalidId() throws Exception {
        when(apartmentService.getApartmentResponseForSidebarById(anyLong())).thenThrow(new EntityNotFoundException("Not found"));
        mockMvc.perform(get("/get-apartment-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}