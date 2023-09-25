package lab.space.my_house_24_user.model.service_bill;

import lab.space.my_house_24_user.model.service.ServiceResponse;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ServiceBillResponse(
        Long id,

        Long count,

        BigDecimal price,

        ServiceResponse service
) {
}
