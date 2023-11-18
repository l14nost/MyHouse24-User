package lab.space.my_house_24_user.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lab.space.my_house_24_user.model.user.UserEditRequest;
import lab.space.my_house_24_user.service.UserService;
import lab.space.my_house_24_user.util.ErrorMapper;
import lab.space.my_house_24_user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {
    private final UserService userService;
    private final UserValidator userValidator;

    @GetMapping("/edit")
    public ModelAndView profileEditPage(){

        try {
            ModelAndView model = new ModelAndView("user/pages/profile/profile-edit");
            model.addObject("user", userService.findUserForEdit());
            return model;
        }
        catch (EntityNotFoundException e){
            return new ModelAndView("redirect:/logout");
        }


    }


    @PutMapping("/edit")
    public ResponseEntity profileEdit(@ModelAttribute @Valid UserEditRequest userEditRequest, BindingResult result){
        if (!userEditRequest.currentPassword().isEmpty()) {
            userValidator.checkCurrentPassword(userEditRequest.currentPassword(), result, "UserEditRequest");
        }
        if (userEditRequest.date()!=null) {
            userValidator.ageValid(userEditRequest.date(), result, "UserAddRequest");
        }
        if (!userEditRequest.password().isEmpty() && !userEditRequest.confirmPassword().isEmpty()) {
            userValidator.passwordMatch(userEditRequest.password(), userEditRequest.confirmPassword(), result, "UserEditRequest");
        }
        if (userEditRequest.email()!=null) {
            userValidator.uniqueEmail(userEditRequest.email(), userEditRequest.id(), result, "UserAddRequest");
        }
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        userService.update(userEditRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping({"","/"})
    public ModelAndView profilePage(){
        return new ModelAndView("user/pages/profile/profile-main");
    }

}
