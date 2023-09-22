package lab.space.my_house_24_user.model.service;

import lab.space.my_house_24_user.model.unit.UnitResponse;
import lombok.Builder;

@Builder
public record ServiceResponse(
        Long id,

        String name,

        UnitResponse unit
) {
}
