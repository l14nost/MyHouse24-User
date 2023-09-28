package lab.space.my_house_24_user.controller;


import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.model.bill.BillRequest;
import lab.space.my_house_24_user.model.bill.BillResponse;
import lab.space.my_house_24_user.model.enums_response.EnumResponse;
import lab.space.my_house_24_user.service.ApartmentService;
import lab.space.my_house_24_user.service.BillService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BillController {
    private final BillService billService;
    private final ApartmentService apartmentService;

    @GetMapping("bills-all")
    public ModelAndView showAllBillsPage(){
        return new ModelAndView("user/pages/bill/bills");
    }

    @GetMapping("bills-{id}")
    public ModelAndView showAllBillsByApartmentIdPage(@PathVariable Long id){
        return new ModelAndView("user/pages/bill/bills");
    }

    @GetMapping("bill-{id}")
    public ModelAndView showBillByIdPage(@PathVariable Long id){
        return new ModelAndView("user/pages/bill/bill-card");
    }

    @GetMapping("/get-all-bill-status")
    public ResponseEntity<List<EnumResponse>> getAllBillStatus() {
        return ResponseEntity.ok(billService.getAllBillStatus());
    }

    @GetMapping("get-bill-{billId}")
    public ResponseEntity<?> getBillById(@PathVariable Long billId){
        try {
            return ResponseEntity.ok(billService.getBillResponseById(billId));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("get-apartment-{id}")
    public ResponseEntity<?> getApartmentById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(apartmentService.getApartmentResponseForSidebarById(id));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @PostMapping("get-bills")
    public ResponseEntity<Page<BillResponse>> getBillById(@RequestBody BillRequest request){
        return ResponseEntity.ok(billService.getBillsResponseByRequest(request));
    }
}
