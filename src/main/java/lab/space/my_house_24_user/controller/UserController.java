package lab.space.my_house_24_user.controller;

import jakarta.persistence.EntityNotFoundException;
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
        try {
            return ResponseEntity.ok(userService.getUserForSidebar());
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body("User not found");
        }
    }

    @GetMapping("/get-user-profile")
    public ResponseEntity getUserProfile(){
        try {
            return ResponseEntity.ok(userService.findUserForProfile());
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body("User not found");
        }
    }
}
