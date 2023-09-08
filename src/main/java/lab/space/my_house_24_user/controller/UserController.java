package lab.space.my_house_24_user.controller;

import lab.space.my_house_24_user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/get-current-user")
    public ResponseEntity getCurrentUser(){
        return ResponseEntity.ok(userService.getUserForSidebar());
    }

    @GetMapping("/get-user-profile")
    public ResponseEntity getUserProfile(){
        return ResponseEntity.ok(userService.findUserForProfile());
    }
}
