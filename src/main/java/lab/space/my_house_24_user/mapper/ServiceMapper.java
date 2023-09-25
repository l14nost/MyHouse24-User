package lab.space.my_house_24_user.mapper;

import lab.space.my_house_24_user.entity.Service;
import lab.space.my_house_24_user.model.service.ServiceResponse;

public interface ServiceMapper {
    static ServiceResponse toServiceResponse(Service service) {
        return ServiceResponse.builder()
                .id(service.getId())
                .name(service.getName())
                .unit(UnitMapper.toUnitResponse(service.getUnit()))
                .build();
    }
}
