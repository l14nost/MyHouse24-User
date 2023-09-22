package lab.space.my_house_24_user.mapper;

import lab.space.my_house_24_user.entity.PriceRate;
import lab.space.my_house_24_user.model.price_rate.PriceRateResponse;

public interface PriceRateMapper {
    static PriceRateResponse toPriceRateResponse(PriceRate priceRate) {
        return PriceRateResponse.builder()
                .id(priceRate.getId())
                .price(priceRate.getPrice())
                .service(ServiceMapper.toServiceResponse(priceRate.getService()))
                .build();
    }
}
