package lab.space.my_house_24_user.model.bill;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lab.space.my_house_24_user.model.enums_response.EnumResponse;
import lab.space.my_house_24_user.model.service_bill.ServiceBillResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public record BillResponse(
        Long id,

        String number,

        @JsonFormat(pattern = "dd.MM.yyyy")
        LocalDate createAt,

        EnumResponse status,

        BigDecimal payed,

        BigDecimal totalPrice,

        List<ServiceBillResponse> serviceBills
) {
}
