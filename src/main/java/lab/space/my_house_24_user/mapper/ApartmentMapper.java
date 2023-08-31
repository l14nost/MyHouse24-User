package lab.space.my_house_24_user.mapper;

import lab.space.my_house_24_user.entity.Apartment;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForSidebar;

public class ApartmentMapper {
    public static ApartmentResponseForSidebar entityToDtoForSidebar(Apartment apartment){
        return ApartmentResponseForSidebar.builder()
                .fullName(apartment.getHouse().getName()+", №"+apartment.getNumber())
                .id(apartment.getId())
                .build();
    }
}
