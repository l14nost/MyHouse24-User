package lab.space.my_house_24_user.model.masters_appl;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForSidebar;
import lab.space.my_house_24_user.model.enums_response.EnumResponse;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record MastersApplicationResponse(
        Long id,

        EnumResponse status,

        EnumResponse masterType,

        ApartmentResponseForSidebar apartment,

        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDate date,

        @JsonFormat(pattern = "HH:mm")
        LocalTime time,

        String description
) {
}
