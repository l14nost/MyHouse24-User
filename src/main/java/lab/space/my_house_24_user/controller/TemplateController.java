package lab.space.my_house_24_user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TemplateController {
    @GetMapping("/index")
    public ModelAndView index(){
        return new ModelAndView("/user/template-pages");
    }
}
