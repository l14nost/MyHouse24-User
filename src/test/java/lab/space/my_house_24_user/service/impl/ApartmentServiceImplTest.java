package lab.space.my_house_24_user.service.impl;

import lab.space.my_house_24_user.entity.Apartment;
import lab.space.my_house_24_user.repository.ApartmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApartmentServiceImplTest {
    @Mock
    private ApartmentRepository apartmentRepository;
    @InjectMocks
    private ApartmentServiceImpl apartmentService;

    @Test
    void findById() {
        when(apartmentRepository.findById(1L)).thenReturn(Optional.of(Apartment.builder().build()));
        Apartment apartment = apartmentService.findById(1L);
        assertEquals(Apartment.builder().build(), apartment);
    }
}