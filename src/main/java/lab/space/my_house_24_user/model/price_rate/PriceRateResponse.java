package lab.space.my_house_24_user.model.price_rate;

import lab.space.my_house_24_user.model.service.ServiceResponse;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PriceRateResponse(
        Long id,

        BigDecimal price,

        ServiceResponse service
) {
}
