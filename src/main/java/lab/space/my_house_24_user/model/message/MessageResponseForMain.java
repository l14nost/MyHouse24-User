package lab.space.my_house_24_user.model.message;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MessageResponseForMain(
        Long id,
        String from,
        String text,
        LocalDateTime sendDate,
        Boolean check
) {
}
