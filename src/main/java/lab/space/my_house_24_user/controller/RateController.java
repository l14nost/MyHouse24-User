package lab.space.my_house_24_user.controller;


import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class RateController {
    private final ApartmentService apartmentService;

    @GetMapping("rate-{id}")
    public ModelAndView showRate(@PathVariable Long id) {
        return new ModelAndView("user/pages/rate/rate");
    }

    @GetMapping("/get-rate-by-{apartmentId}")
    public ResponseEntity<?> getRate(@PathVariable Long apartmentId) {
        try {
            return ResponseEntity.ok(apartmentService.getApartmentResponseForRateById(apartmentId));
        }catch (EntityNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
}
