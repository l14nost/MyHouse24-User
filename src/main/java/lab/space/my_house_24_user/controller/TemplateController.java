package lab.space.my_house_24_user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TemplateController {
    @GetMapping("/index")
    public String index(){
        return "/user/template-pages";
    }
}
