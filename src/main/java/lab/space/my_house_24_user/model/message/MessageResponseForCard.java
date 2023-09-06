package lab.space.my_house_24_user.model.message;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record MessageResponseForCard(
        Long id,
        String title,
        String message,
        String staffFullName,
        LocalDateTime sendDate


) {
}
