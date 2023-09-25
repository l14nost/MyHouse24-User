package lab.space.my_house_24_user.controller;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ErrorController {

    @RequestMapping("/error-404")
    public ModelAndView handle404() {
        return new ModelAndView("user/pages/error/404");
    }



    @GetMapping("/access-denied")
    public ModelAndView accessDenied() {
        return new ModelAndView("user/pages/error/403");
    }
}