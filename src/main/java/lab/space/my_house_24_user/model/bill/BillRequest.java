package lab.space.my_house_24_user.model.bill;

import lab.space.my_house_24_user.enums.BillStatus;

public record BillRequest(
        int pageIndex,

        String numberQuery,

        Long apartmentIdQuery,

        String dateQuery,

        BillStatus billStatusQuery,

        String payedQuery,

        String priceQuery
) {
}
