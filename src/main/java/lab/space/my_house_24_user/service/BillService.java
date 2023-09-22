package lab.space.my_house_24_user.service;

import lab.space.my_house_24_user.model.bill.BillRequest;
import lab.space.my_house_24_user.model.bill.BillResponse;
import org.springframework.data.domain.Page;

public interface BillService {
    BillResponse getBillResponseById(Long id);

    Page<BillResponse> getBillsResponseByRequest(BillRequest request);
}
