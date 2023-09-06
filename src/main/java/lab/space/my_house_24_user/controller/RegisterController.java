package lab.space.my_house_24_user.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24_user.model.auth.RegisterRequest;
import lab.space.my_house_24_user.service.UserService;
import lab.space.my_house_24_user.util.ErrorMapper;
import lab.space.my_house_24_user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserValidator userValidator;
    private final UserService userService;


    @GetMapping({"/register/", "/register"})
    public String showLogin() {
        return "/user/pages/auth/register";
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterRequest registerRequest, BindingResult result){
        if (!registerRequest.password().isEmpty() && !registerRequest.confirmPassword().isEmpty()) {
            userValidator.passwordMatch(registerRequest.password(), registerRequest.confirmPassword(), result, "UserEditRequest");
        }
        if (registerRequest.email()!=null) {
            userValidator.uniqueEmail(registerRequest.email(), 0L, result, "UserAddRequest");
        }
        userValidator.checkConfirm(registerRequest.confirm(), result, "UserAddRequest");
        if (result.hasErrors()){
            System.out.println(result.getAllErrors());
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        userService.register(registerRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/privacy-policy")
    public String showPrivacy(){
        return "/user/pages/auth/privacy-policy";
    }


}