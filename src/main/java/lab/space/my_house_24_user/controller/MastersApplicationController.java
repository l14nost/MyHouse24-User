package lab.space.my_house_24_user.controller;


import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lab.space.my_house_24_user.model.apartment.ApartmentResponseForSidebar;
import lab.space.my_house_24_user.model.enums_response.EnumResponse;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationRequest;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationResponse;
import lab.space.my_house_24_user.model.masters_appl.MastersApplicationSaveRequest;
import lab.space.my_house_24_user.service.ApartmentService;
import lab.space.my_house_24_user.service.MastersApplicationService;
import lab.space.my_house_24_user.util.ErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("masters-applications")
@RequiredArgsConstructor
public class MastersApplicationController {
    private final MastersApplicationService mastersApplicationService;
    private final ApartmentService apartmentService;

    @GetMapping({"/", ""})
    public String showMastersApplicationPage() {
        return "user/pages/masters_application/masters-application";
    }

    @GetMapping("/add")
    public String showMastersApplicationSavePage() {
        return "user/pages/masters_application/masters-application-save";
    }

    @GetMapping("/get-all-type-master")
    public ResponseEntity<List<EnumResponse>> getAllTypeMaster() {
        return ResponseEntity.ok(mastersApplicationService.getAllTypeMaster());
    }

    @GetMapping("/get-all-status")
    public ResponseEntity<List<EnumResponse>> getAllStatus() {
        return ResponseEntity.ok(mastersApplicationService.getAllMastersApplicationStatus());
    }

    @GetMapping("/get-all-apartment")
    public ResponseEntity<List<ApartmentResponseForSidebar>> getAllApartment() {
        return ResponseEntity.ok(apartmentService.getAllApartmentResponse());
    }

    @PostMapping("/get-all-master-call")
    public ResponseEntity<Page<MastersApplicationResponse>> getAllMastersApplication(@RequestBody MastersApplicationRequest request) {
        return ResponseEntity.ok(mastersApplicationService.getAllMastersApplicationByRequest(request));
    }

    @PostMapping("/save-master-call")
    public ResponseEntity<?> saveMastersApplication(@Valid @RequestBody MastersApplicationSaveRequest request,
                                                    BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        try {
            mastersApplicationService.saveMastersApplicationByRequest(request);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/delete-master-call/{id}")
    public ResponseEntity<?> deleteMastersApplicationById(@PathVariable Long id) {
        if (id < 1) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Id must be > 0");
        }
        try {
            mastersApplicationService.deleteMastersApplicationById(id);
            return ResponseEntity.ok().build();
        } catch (EntityNotFoundException | IllegalArgumentException e) {
            if (e instanceof EntityNotFoundException)
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
