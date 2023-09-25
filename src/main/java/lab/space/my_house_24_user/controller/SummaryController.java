package lab.space.my_house_24_user.controller;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.service.ApartmentService;
import lab.space.my_house_24_user.service.SummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequiredArgsConstructor
public class SummaryController {
    private final SummaryService summaryService;



    @GetMapping("/summary-{id}")
    public ModelAndView summaryPage(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("user/pages/statistic/statistic");
        modelAndView.addObject("id", id);
        return modelAndView;
    }

    @GetMapping("/get-summary-by-id/{id}")
    public ResponseEntity getSummaryById(@PathVariable Long id){
        try {
            return ResponseEntity.ok(summaryService.summaryByApartment(id));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body("Apartment not found");
        }
    }
}
