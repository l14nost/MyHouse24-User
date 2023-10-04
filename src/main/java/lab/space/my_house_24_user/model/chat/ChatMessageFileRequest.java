package lab.space.my_house_24_user.model.chat;

import org.springframework.web.multipart.MultipartFile;

public record ChatMessageFileRequest(
        Long id,
        MultipartFile file
) {
}
