package lab.space.my_house_24_user.service;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.entity.Apartment;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForRate;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForSidebar;

import java.util.List;

public interface ApartmentService {
    Apartment findById(Long id);

    List<ApartmentResponseForSidebar> getAllApartmentResponse();

    ApartmentResponseForRate getApartmentResponseForRateById(Long id) throws EntityNotFoundException;

    ApartmentResponseForSidebar getApartmentResponseForSidebarById(Long id) throws EntityNotFoundException;
}
