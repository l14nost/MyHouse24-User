package lab.space.my_house_24_user.model.chat;

import lab.space.my_house_24_user.enums.MessageType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatMessageResponse(
        Long id,
        String message,
        String filename,
        MessageType type,
        Long userId,
        String userAvatar,
        String userName,

        String time
) {
}
