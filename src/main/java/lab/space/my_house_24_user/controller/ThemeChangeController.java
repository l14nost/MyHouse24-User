package lab.space.my_house_24_user.controller;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ThemeChangeController {
    private final UserService userService;

    @GetMapping("/get-theme")
    public ResponseEntity getTheme(){
        try {
            User user = userService.findById(userService.getCurrentUser());
            return ResponseEntity.ok(user.getTheme());
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }

    }

    @PostMapping("/change-theme")
    public ResponseEntity changeTheme(@RequestParam Boolean theme){
        userService.changeTheme(theme);
        return ResponseEntity.ok().build();
    }
}
