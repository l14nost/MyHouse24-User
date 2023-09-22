package lab.space.my_house_24_user.service;

import lab.space.my_house_24_user.model.bill.BillRequest;
import lab.space.my_house_24_user.model.bill.BillResponse;
import lab.space.my_house_24_user.model.enums_response.EnumResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BillService {
    BillResponse getBillResponseById(Long id);

    Page<BillResponse> getBillsResponseByRequest(BillRequest request);

    List<EnumResponse> getAllBillStatus();
}
