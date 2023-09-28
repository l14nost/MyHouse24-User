package lab.space.my_house_24_user.controller;

import lab.space.my_house_24_user.model.apartment.ApartmentResponseForSidebar;
import lab.space.my_house_24_user.service.ApartmentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.ui.Model;

import java.util.List;

import static org.mockito.Mockito.*;

@WebMvcTest(GlobalControllerAdvice.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class GlobalControllerAdviceTest {

    @InjectMocks
    private GlobalControllerAdvice globalControllerAdvice;

    @MockBean
    private ApartmentService apartmentService;

    @Test
    public void testGetApartments() {

        List<ApartmentResponseForSidebar> apartments = List.of(ApartmentResponseForSidebar.builder().id(1L).fullName("dadasd").build());
        when(apartmentService.getAllApartmentResponse()).thenReturn(apartments);
        globalControllerAdvice = new GlobalControllerAdvice(apartmentService);

        Model model = mock(Model.class);

        globalControllerAdvice.getApartments();

        verify(apartmentService, times(1)).getAllApartmentResponse();
    }
}
