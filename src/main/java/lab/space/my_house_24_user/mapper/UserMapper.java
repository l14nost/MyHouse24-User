package lab.space.my_house_24_user.mapper;

import lab.space.my_house_24_user.entity.Apartment;
import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForSidebar;
import lab.space.my_house_24_user.model.user.UserResponseForSidebar;

public class UserMapper {
    public static UserResponseForSidebar entityToDtoForSidebar(User user){
        return UserResponseForSidebar.builder()
                .id(user.getId())
                .fullName(user.getSurname()+" "+user.getFirstname()+" "+user.getLastname())
                .apartments(user.getApartmentList().stream().map(ApartmentMapper::entityToDtoForSidebar).toList())
                .build();
    }
}
