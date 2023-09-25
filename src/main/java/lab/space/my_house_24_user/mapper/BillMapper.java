package lab.space.my_house_24_user.mapper;

import lab.space.my_house_24_user.entity.Bill;
import lab.space.my_house_24_user.model.bill.BillResponse;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.ZoneId;

public interface BillMapper {
    static BillResponse toBillResponse(Bill bill) {
        return BillResponse.builder()
                .id(bill.getId())
                .number(bill.getNumber())
                .createAt(bill.getCreateAt().atZone(ZoneId.systemDefault()).toLocalDate())
                .status(EnumMapper.toSimpleDto(
                        bill.getStatus().name(),
                        bill.getStatus().getBillStatus(LocaleContextHolder.getLocale()))
                )
                .payed(bill.getPayed())
                .totalPrice(bill.getTotalPrice())
                .serviceBills(bill.getServiceBillList().stream().map(ServiceBillMapper::toServiceBillResponse).toList())
                .build();
    }
}
