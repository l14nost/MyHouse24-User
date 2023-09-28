package lab.space.my_house_24_user.service.impl;

import lab.space.my_house_24_user.entity.Bill;
import lab.space.my_house_24_user.enums.BillStatus;
import lab.space.my_house_24_user.model.bill.BillRequest;
import lab.space.my_house_24_user.model.bill.BillResponse;
import lab.space.my_house_24_user.model.enums_response.EnumResponse;
import lab.space.my_house_24_user.repository.BillRepository;
import lab.space.my_house_24_user.specification.BillSpecification;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BillServiceImplTest {

    @InjectMocks
    private BillServiceImpl billService;

    @Mock
    private BillRepository billRepository;

    @Mock
    private BillSpecification billSpecification;

    @Test
    public void testGetBillResponseById() {
        Long billId = 1L;
        Bill bill = new Bill();
        bill.setId(billId);
        bill.setCreateAt(LocalDateTime.now());
        bill.setStatus(BillStatus.PAID);

        when(billRepository.findById(billId)).thenReturn(Optional.of(bill));

        BillResponse actualResponse = billService.getBillResponseById(billId);

        assertNotNull(actualResponse);
        assertEquals(billId, actualResponse.id());
    }

    @Test
    public void testGetBillsResponseByRequest() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        when(authentication.getName()).thenReturn("testUser");
        when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        BillRequest request = BillRequest.builder().build();
        Page<Bill> mockPage = new PageImpl<>(List.of(new Bill().setId(1L).setCreateAt(LocalDateTime.now()).setStatus(BillStatus.PAID)));

        when(billRepository.findAll((Specification<Bill>) any(), any(PageRequest.class))).thenReturn(mockPage);

        Page<BillResponse> responsePage = billService.getBillsResponseByRequest(request);

        assertNotNull(responsePage);
    }

    @Test
    public void testGetAllBillStatus() {
        List<EnumResponse> result = billService.getAllBillStatus();

        assertNotNull(result);
        assertFalse(result.isEmpty());
    }
}