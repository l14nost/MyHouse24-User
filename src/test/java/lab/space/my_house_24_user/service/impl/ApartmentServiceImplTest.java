package lab.space.my_house_24_user.service.impl;

import lab.space.my_house_24_user.entity.*;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForRate;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForSidebar;
import lab.space.my_house_24_user.repository.ApartmentRepository;
import lab.space.my_house_24_user.specification.ApartmentSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ApartmentServiceImplTest {
    @Mock
    private ApartmentRepository apartmentRepository;
    @Mock
    private ApartmentSpecification apartmentSpecification;
    @InjectMocks
    private ApartmentServiceImpl apartmentService;

    @Test
    void findById() {
        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(Apartment.builder().build()));
        Apartment apartment = apartmentService.findById(1L);
        assertEquals(Apartment.builder().build(), apartment);
    }

    @Test
    public void testGetApartmentResponseForRateById() {
        Long apartmentId = 1L;
        Apartment apartment = new Apartment();
        apartment.setId(apartmentId);
        apartment.setNumber(101010);
        House house = new House();
        house.setName("qweqwe");
        apartment.setHouse(house);
        Rate rate = new Rate()
                .setId(1L)
                .setPriceRateList(List.of(new PriceRate()
                        .setId(1L)
                        .setPrice(BigDecimal.ZERO)
                        .setService(new Service()
                                .setId(1L)
                                .setName("sadad")
                                .setUnit(new Unit()
                                        .setId(1L)
                                        .setName("asdad")))));
        apartment.setRate(rate);

        when(apartmentRepository.findById(apartmentId)).thenReturn(Optional.of(apartment));

        ApartmentResponseForRate actualResponse = apartmentService.getApartmentResponseForRateById(apartmentId);

        assertNotNull(actualResponse);
        assertEquals(apartmentId, actualResponse.id());
    }

    @Test
    public void testGetApartmentResponseForSidebarById() {
        Long apartmentId = 1L;
        Apartment apartment = new Apartment();
        apartment.setId(apartmentId);
        apartment.setNumber(101010);
        House house = new House();
        house.setName("qweqwe");
        apartment.setHouse(house);


        when(apartmentRepository.findById(apartmentId)).thenReturn(Optional.of(apartment));

        ApartmentResponseForSidebar actualResponse = apartmentService.getApartmentResponseForSidebarById(apartmentId);

        assertNotNull(actualResponse);
        assertEquals(apartmentId, actualResponse.id());
    }

    @Test
    public void testGetAllApartmentResponse() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        List<Apartment> apartmentList = new ArrayList<>();

        Apartment apartment = new Apartment();
        apartment.setId(1L);
        apartment.setNumber(101010);
        House house = new House();
        house.setName("qweqwe");
        apartment.setHouse(house);

        apartmentList.add(apartment);
        apartment.setId(2L);
        apartmentList.add(apartment);


        when(apartmentRepository.findAll((Specification<Apartment>) any())).thenReturn(apartmentList);

        List<ApartmentResponseForSidebar> responseList = apartmentService.getAllApartmentResponse();

        assertNotNull(responseList);
    }
}