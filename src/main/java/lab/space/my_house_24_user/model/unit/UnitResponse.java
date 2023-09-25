package lab.space.my_house_24_user.model.unit;

import lombok.Builder;

@Builder
public record UnitResponse(
        Long id,

        String name
) {
}
