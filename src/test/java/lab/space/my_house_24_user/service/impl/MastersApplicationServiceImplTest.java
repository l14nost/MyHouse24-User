package lab.space.my_house_24_user.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.entity.Apartment;
import lab.space.my_house_24_user.entity.House;
import lab.space.my_house_24_user.entity.MastersApplication;
import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.enums.Master;
import lab.space.my_house_24_user.enums.MastersApplicationStatus;
import lab.space.my_house_24_user.model.enums_response.EnumResponse;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationRequest;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationResponse;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationSaveRequest;
import lab.space.my_house_24_user.repository.MastersApplicationRepository;
import lab.space.my_house_24_user.service.ApartmentService;
import lab.space.my_house_24_user.service.UserService;
import lab.space.my_house_24_user.specification.MastersApplicationSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MastersApplicationServiceImplTest {

    @InjectMocks
    private MastersApplicationServiceImpl mastersApplicationService;

    @Mock
    private MastersApplicationRepository mastersApplicationRepository;

    @Mock
    private MastersApplicationSpecification specification;

    @Mock
    private UserService userService;

    @Mock
    private ApartmentService apartmentService;

    @Test
    public void testGetMastersApplicationById() {
        MastersApplication mastersApplication = new MastersApplication();
        mastersApplication.setId(1L);
        when(mastersApplicationRepository.findById(1L)).thenReturn(Optional.of(mastersApplication));

        MastersApplication result = mastersApplicationService.getMastersApplicationById(1L);
        assertEquals(1L, result.getId());
    }

    @Test
    public void testGetMastersApplicationByIdNotFound() {
        when(mastersApplicationRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            mastersApplicationService.getMastersApplicationById(1L);
        });
    }

    @Test
    public void testGetMastersApplicationResponse() {
        Long id = 1L;
        MastersApplication mastersApplication = new MastersApplication();
        mastersApplication.setId(id);
        mastersApplication.setMastersApplicationStatus(MastersApplicationStatus.IN_PROCESS);
        mastersApplication.setMaster(Master.ANY_MASTER);

        Apartment apartment = new Apartment();
        apartment.setId(1L);
        apartment.setNumber(101010);
        House house = new House();
        house.setName("qweqwe");
        apartment.setHouse(house);

        mastersApplication.setApartment(apartment);
        mastersApplication.setDateTime(LocalDateTime.now());
        mastersApplication.setDescription("asdads");

        when(mastersApplicationRepository.findById(id)).thenReturn(Optional.of(mastersApplication));

        MastersApplicationResponse result = mastersApplicationService.getMastersApplicationResponse(id);
        assertEquals(1L, result.id());
    }

    @Test
    public void testGetAllMastersApplicationByRequest() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        MastersApplicationRequest request = MastersApplicationRequest.builder().pageIndex(1).build();

        User user = new User();
        user.setId(1L);
        user.setEmail("testUser");
        MastersApplication mastersApplication = new MastersApplication();
        mastersApplication.setId(1L);
        mastersApplication.setMastersApplicationStatus(MastersApplicationStatus.IN_PROCESS);
        mastersApplication.setMaster(Master.ANY_MASTER);
        mastersApplication.setUser(user);

        Apartment apartment = new Apartment();
        apartment.setId(1L);
        apartment.setNumber(101010);
        House house = new House();
        house.setName("qweqwe");
        apartment.setHouse(house);

        mastersApplication.setApartment(apartment);
        mastersApplication.setDateTime(LocalDateTime.now());
        mastersApplication.setDescription("asdads");

        List<MastersApplication> mastersApplications = new ArrayList<>();
        mastersApplications.add(mastersApplication);
        mastersApplication.setId(2L);
        mastersApplications.add(mastersApplication);
        mastersApplication.setId(3L);
        mastersApplications.add(mastersApplication);

        Page<MastersApplication> page = new PageImpl<>(mastersApplications);

        when(mastersApplicationRepository.findAll((Specification<MastersApplication>) any(), any(PageRequest.class))).thenReturn(page);

        Page<MastersApplicationResponse> result = mastersApplicationService.getAllMastersApplicationByRequest(request);
        assertNotNull(result);

    }

    @Test
    public void testGetAllMastersApplicationStatus() {
        List<EnumResponse> result = mastersApplicationService.getAllMastersApplicationStatus();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testGetAllTypeMaster() {
        List<EnumResponse> result = mastersApplicationService.getAllTypeMaster();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }

    @Test
    public void testSaveMastersApplicationByRequest() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        MastersApplicationSaveRequest request = MastersApplicationSaveRequest.builder()
                .apartmentId(1L)
                .description("aasd")
                .masterType(Master.ANY_MASTER)
                .date(LocalDate.now())
                .time(LocalTime.now())
                .build();

        assertDoesNotThrow(() -> {
            mastersApplicationService.saveMastersApplicationByRequest(request);
        });
    }

    @Test
    public void testDeleteMastersApplicationById() {
        MastersApplication mastersApplication = new MastersApplication();
        mastersApplication.setId(1L);
        mastersApplication.setMastersApplicationStatus(MastersApplicationStatus.NEW);
        when(mastersApplicationRepository.findById(1L)).thenReturn(Optional.of(mastersApplication));

        assertDoesNotThrow(() -> {
            mastersApplicationService.deleteMastersApplicationById(1L);
        });
    }

    @Test
    public void testDeleteMastersApplicationByIdNotAllowed() {
        MastersApplication mastersApplication = new MastersApplication();
        mastersApplication.setId(1L);
        mastersApplication.setMastersApplicationStatus(MastersApplicationStatus.IN_PROCESS);
        when(mastersApplicationRepository.findById(1L)).thenReturn(Optional.of(mastersApplication));

        assertThrows(IllegalArgumentException.class, () -> {
            mastersApplicationService.deleteMastersApplicationById(1L);
        });
    }
}
