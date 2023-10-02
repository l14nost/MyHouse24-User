package lab.space.my_house_24_user.model.bill;

import lab.space.my_house_24_user.enums.BillStatus;
import lombok.Builder;

@Builder
public record BillRequest(
        int pageIndex,

        String numberQuery,

        Long apartmentIdQuery,

        String dateQuery,

        BillStatus statusQuery,

        String payedQuery,

        String priceQuery
) {
}
