package lab.space.my_house_24_user.model.apartment;

import lombok.Builder;

@Builder
public record ApartmentResponseForProfile(
        String fullName,
        String houseName,
        String address,
        Integer number,
        Double area,
        String sectionName,
        String floorName,
        String bankBookNumber,
        String image1,
        String image2,
        String image3,
        String image4,
        String image5
) {
}
