package lab.space.my_house_24_user.model.masters_appl;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lab.space.my_house_24_user.enums.Master;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
public record MastersApplicationSaveRequest(

        @NotNull(message = "{not.blank.message}")
        Master masterType,

        @NotNull(message = "{not.blank.message}")
        @Min(1)
        Long apartmentId,

        @NotNull(message = "{not.blank.message}")
        @JsonDeserialize(using = LocalDateDeserializer.class)
        LocalDate date,

        @NotNull(message = "{not.blank.message}")
        @JsonDeserialize(using = LocalTimeDeserializer.class)
        LocalTime time,

        @Size(max = 1000, message = "{size.less.message}" + " {max}")
        @NotBlank(message = "{not.blank.message}")
        String description
) {
}
