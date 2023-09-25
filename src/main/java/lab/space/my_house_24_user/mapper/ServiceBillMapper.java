package lab.space.my_house_24_user.mapper;

import lab.space.my_house_24_user.model.service_bill.ServiceBillResponse;

public interface ServiceBillMapper {
    static ServiceBillResponse toServiceBillResponse(lab.space.my_house_24_user.entity.ServiceBill serviceBill) {
        return ServiceBillResponse.builder()
                .id(serviceBill.getId())
                .count(serviceBill.getCount())
                .price(serviceBill.getTotalPrice())
                .service(ServiceMapper.toServiceResponse(serviceBill.getService()))
                .build();
    }
}
