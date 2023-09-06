package lab.space.my_house_24_user.controller;

import jakarta.validation.Valid;
import lab.space.my_house_24_user.model.auth.ForgotPassRequest;
import lab.space.my_house_24_user.model.auth.ForgotRequest;
import lab.space.my_house_24_user.service.JwtService;
import lab.space.my_house_24_user.service.UserService;
import lab.space.my_house_24_user.util.ErrorMapper;
import lab.space.my_house_24_user.validator.UserValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/login")
@RequiredArgsConstructor
public class LoginController {

    private final UserValidator userValidator;
    private final UserService userService;
    private final JwtService jwtService;


    @GetMapping({"", "/"})
    public String showLogin() {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof User){
            return "redirect:/index";
        }else {
            return "/user/pages/auth/login";
        }
    }


    @GetMapping({"/forgot-password-send"})
    public String showForgot() {
        return "/user/pages/auth/forgot-password";
    }

    @PostMapping("/forgot-password-send")
    public ResponseEntity sendForgot(@RequestBody @Valid ForgotRequest forgotRequest, BindingResult result){
        if (forgotRequest.email()!=null && !forgotRequest.email().isBlank()) {
            userValidator.checkStatus(forgotRequest.email(), result, "ForgotRequest");
            userValidator.existByEmail(forgotRequest.email(), result, "ForgotRequest");
        }
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(result));
        }
        userService.sendForgotPasswordLetter(forgotRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/forgot-password/{token}")
    public String showForgotPasswordChangePage(@PathVariable String token, Model model) {
        UserDetails userDetails = userService.loadUserByToken(token);
        if (!jwtService.isTokenValid(
                token,
                userDetails,
                userService.findUserByEmail(userDetails.getUsername()))) {
            return "/user/pages/auth/new-password-error";
        } else {
            model.addAttribute("token", token);
            return "/user/pages/auth/new-password";
        }
    }

    @PutMapping("/forgot-password/{token}")
    public ResponseEntity<?> forgotPasswordStaff(@PathVariable String token, @Valid @RequestBody ForgotPassRequest forgotPassRequest,
                                                 BindingResult bindingResult) {
        userValidator.passwordMatch(forgotPassRequest.password(), forgotPassRequest.confirmPassword(), bindingResult, "ForgotPassRequest");
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(ErrorMapper.mapErrors(bindingResult));
        }
        userService.forgotPassword(forgotPassRequest, token);
        return ResponseEntity.ok().build();
    }


}