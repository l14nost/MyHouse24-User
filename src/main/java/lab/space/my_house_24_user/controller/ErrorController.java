package lab.space.my_house_24_user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @RequestMapping("/error-404")
    public ModelAndView handle404() {
        return new ModelAndView("/user/pages/error/404");
    }
}