package lab.space.my_house_24_user.mapper;

import lab.space.my_house_24_user.entity.Apartment;
import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForSidebar;
import lab.space.my_house_24_user.model.user.UserResponseForEdit;
import lab.space.my_house_24_user.model.user.UserResponseForProfile;
import lab.space.my_house_24_user.model.user.UserResponseForSidebar;

import java.time.ZoneId;

public class UserMapper {
    public static UserResponseForSidebar entityToDtoForSidebar(User user){
        return UserResponseForSidebar.builder()
                .id(user.getId())
                .fullName(user.getSurname()+" "+user.getFirstname()+" "+user.getLastname())
                .apartments(user.getApartmentList().stream().map(ApartmentMapper::entityToDtoForSidebar).toList())
                .filename(user.getFilename())
                .build();
    }


    public  static UserResponseForEdit entityToEditDto(User user){
        return UserResponseForEdit.builder()
                .id(user.getId())
                .email(user.getEmail())
                .lastname(user.getLastname())
                .firstname(user.getFirstname())
                .surname(user.getSurname())
                .number(user.getNumber())
                .filename(user.getFilename())
                .date(user.getDate().atZone(ZoneId.systemDefault()).toLocalDate())
                .viber(user.getViber())
                .telegram(user.getTelegram())
                .notes(user.getNotes())
                .build();
    }

    public static UserResponseForProfile entityToProfileDto(User user){
        return UserResponseForProfile.builder()
                .id(user.getId())
                .filename(user.getFilename())
                .notes(user.getNotes())
                .number(user.getNumber())
                .telegram(user.getTelegram())
                .viber(user.getViber())
                .fullName(user.getSurname()+" "+user.getFirstname()+" "+user.getLastname())
                .apartments(user.getApartmentList().stream().map(ApartmentMapper::entityToDtoForProfile).toList())
                .email(user.getEmail())
                .build();
    }
}
