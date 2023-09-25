package lab.space.my_house_24_user.model.rate;

import lab.space.my_house_24_user.model.price_rate.PriceRateResponse;
import lombok.Builder;

import java.util.List;

@Builder
public record RateResponse(
        Long id,

        List<PriceRateResponse> priceRates
) {
}
