package lab.space.my_house_24_user.model.user;

import lab.space.my_house_24_user.model.apartment.ApartmentResponseForSidebar;
import lombok.Builder;

import java.util.List;
@Builder
public record UserResponseForSidebar(
        Long id,
        String fullName,
        String filename,
        List <ApartmentResponseForSidebar> apartments
) {
}
