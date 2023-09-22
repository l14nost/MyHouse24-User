package lab.space.my_house_24_user.mapper;

import lab.space.my_house_24_user.entity.Rate;
import lab.space.my_house_24_user.model.rate.RateResponse;

public interface RateMapper {
    static RateResponse toRateResponse(Rate rate) {
        return RateResponse.builder()
                .id(rate.getId())
                .priceRates(rate.getPriceRateList().stream().map(PriceRateMapper::toPriceRateResponse).toList())
                .build();
    }
}
