package lab.space.my_house_24_user.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.entity.Apartment;
import lab.space.my_house_24_user.repository.ApartmentRepository;
import lab.space.my_house_24_user.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    @Override
    public Apartment findById(Long id) {
        return apartmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Apartment by id " + id + " not found"));
    }

}
