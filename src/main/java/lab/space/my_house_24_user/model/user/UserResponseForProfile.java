package lab.space.my_house_24_user.model.user;

import lab.space.my_house_24_user.model.apartment.ApartmentResponseForProfile;
import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Builder
public record UserResponseForProfile(
        Long id,
        String filename,
        String fullName,
        String number,
        String telegram,
        String viber,
        String email,
        String notes,
        List<ApartmentResponseForProfile> apartments
) {
}
