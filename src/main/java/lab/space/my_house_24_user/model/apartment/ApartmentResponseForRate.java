package lab.space.my_house_24_user.model.apartment;

import lab.space.my_house_24_user.model.rate.RateResponse;
import lombok.Builder;

@Builder
public record ApartmentResponseForRate(
        Long id,

        String fullName,

        RateResponse rate
) {

}
