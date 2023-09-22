package lab.space.my_house_24_user.mapper;

import lab.space.my_house_24_user.entity.Apartment;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForRate;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForProfile;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForSidebar;

public class ApartmentMapper {
    public static ApartmentResponseForSidebar entityToDtoForSidebar(Apartment apartment){
        return ApartmentResponseForSidebar.builder()
                .fullName(apartment.getHouse().getName()+", №"+apartment.getNumber())
                .id(apartment.getId())
                .build();
    }

    public static ApartmentResponseForProfile entityToDtoForProfile(Apartment apartment){
        String bankBook = "-";
        if (apartment.getBankBook()!=null){
            bankBook = apartment.getBankBook().getNumber();
        }
        return ApartmentResponseForProfile.builder()
                .fullName(apartment.getHouse().getName()+". "+apartment.getHouse().getAddress()+", №"+apartment.getNumber())
                .houseName(apartment.getHouse().getName())
                .address(apartment.getHouse().getAddress())
                .area(apartment.getArea())
                .number(apartment.getNumber())
                .bankBookNumber(bankBook)
                .floorName(apartment.getFloor().getName())
                .sectionName(apartment.getSection().getName())
                .image1(apartment.getHouse().getImage1())
                .image2(apartment.getHouse().getImage2())
                .image3(apartment.getHouse().getImage3())
                .image4(apartment.getHouse().getImage4())
                .image5(apartment.getHouse().getImage5())
                .build();
    }

    public static ApartmentResponseForRate toApartmentForRate(Apartment apartment){
        return ApartmentResponseForRate.builder()
                .id(apartment.getId())
                .fullName(apartment.getHouse().getName()+", №"+apartment.getNumber())
                .rate(RateMapper.toRateResponse(apartment.getRate()))
                .build();
    }
}
