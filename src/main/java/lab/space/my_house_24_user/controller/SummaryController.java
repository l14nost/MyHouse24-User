package lab.space.my_house_24_user.controller;

import lab.space.my_house_24_user.service.ApartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequiredArgsConstructor
public class SummaryController {
    private final ApartmentService apartmentService;

    @GetMapping("/test-summary")
    public ModelAndView testSummary(){
        return new ModelAndView("/user/pages/statistic/statistic");
    }


    @GetMapping("/summary/{id}")
    public ModelAndView summaryPage(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("/user/pages/statistic/statistic");
        modelAndView.addObject("bankBook", apartmentService.bankBookByApartment(id));
        return modelAndView;
    }
}
