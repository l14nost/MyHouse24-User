package lab.space.my_house_24_user.model.user;

import lombok.Builder;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder
public record UserResponseForEdit(
        Long id,
        String filename,
        String lastname,
        String firstname,
        String surname,
        String number,
        String telegram,
        String viber,
        String email,
        @DateTimeFormat(pattern = "yyyy-MM-dd")
        LocalDate date,
        String notes
) {
}
