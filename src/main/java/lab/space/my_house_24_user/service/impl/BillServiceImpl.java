package lab.space.my_house_24_user.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.mapper.BillMapper;
import lab.space.my_house_24_user.model.bill.BillRequest;
import lab.space.my_house_24_user.model.bill.BillResponse;
import lab.space.my_house_24_user.repository.BillRepository;
import lab.space.my_house_24_user.service.BillService;
import lab.space.my_house_24_user.specification.BillSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final BillSpecification billSpecification;

    @Override
    public BillResponse getBillResponseById(Long id) throws EntityNotFoundException {
        log.info("Try to get Bill and Convert in Response");
        return BillMapper.toBillResponse(billRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found")));
    }

    @Override
    public Page<BillResponse> getBillsResponseByRequest(BillRequest request) {
        log.info("Try to get All Bills and Convert in Response");
        return billRepository
                .findAll(billSpecification.getBillByRequest(request, SecurityContextHolder.getContext().getAuthentication().getName()), PageRequest.of(request.pageIndex(), 10))
                .map(BillMapper::toBillResponse);
    }
}
