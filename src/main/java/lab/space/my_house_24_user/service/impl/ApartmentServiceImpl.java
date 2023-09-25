package lab.space.my_house_24_user.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.entity.Apartment;
import lab.space.my_house_24_user.repository.ApartmentRepository;
import lab.space.my_house_24_user.service.ApartmentService;
import lab.space.my_house_24_user.specification.ApartmentSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApartmentServiceImpl implements ApartmentService {
    private final ApartmentRepository apartmentRepository;
    private final ApartmentSpecification apartmentSpecification;
    @Override
    public Apartment findById(Long id) {
        log.info("Try to find apartment by id: "+id);
        return apartmentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Apartment by id " + id + " not found"));
    }

    @Override
    public String bankBookByApartment(Long id) {
        Apartment apartment = findById(id);
        if (apartment.getBankBook()!=null) {
            return apartment.getBankBook().getNumber();
        }
        return "-";
    }

    @Override
    public List<ApartmentResponseForSidebar> getAllApartmentResponse() {
        log.info("Try to get All Apartments by Context");
        return apartmentRepository
                .findAll(apartmentSpecification.getApartmentByUserEmail(SecurityContextHolder.getContext().getAuthentication().getName()))
                .stream()
                .map(ApartmentMapper::entityToDtoForSidebar)
                .toList();
    }

    @Override
    public ApartmentResponseForRate getApartmentResponseForRateById(Long id) throws EntityNotFoundException{
        log.info("Try to Convert to ApartmentResponseForRate");
        return ApartmentMapper.toApartmentForRate(findById(id));
    }

    @Override
    public ApartmentResponseForSidebar getApartmentResponseForSidebarById(Long id) throws EntityNotFoundException{
        log.info("Try to Convert to ApartmentResponseForRate");
        return ApartmentMapper.entityToDtoForSidebar(findById(id));
    }

}
