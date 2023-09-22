package lab.space.my_house_24_user.model.enums_response;

import lombok.Builder;

@Builder
public record EnumResponse(
        String name,
        String value
) {
}
