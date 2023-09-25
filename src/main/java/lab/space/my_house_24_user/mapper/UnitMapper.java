package lab.space.my_house_24_user.mapper;

import lab.space.my_house_24_user.entity.Unit;
import lab.space.my_house_24_user.model.unit.UnitResponse;

public interface UnitMapper {
    static UnitResponse toUnitResponse(Unit unit) {
        return UnitResponse.builder()
                .id(unit.getId())
                .name(unit.getName())
                .build();
    }
}
