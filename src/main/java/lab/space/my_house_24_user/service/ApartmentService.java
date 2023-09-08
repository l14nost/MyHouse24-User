package lab.space.my_house_24_user.service;

import lab.space.my_house_24_user.entity.Apartment;

public interface ApartmentService {
    Apartment findById(Long id);

    String bankBookByApartment(Long id);
}
