package lab.space.my_house_24_user.controller;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.model.message.MessageMainPageRequest;
import lab.space.my_house_24_user.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    @GetMapping("")
    public ModelAndView messagePage(){
        return new ModelAndView("user/pages/message/message-main");
    }


    @PostMapping("/get-all-message")
    public ResponseEntity getAllMessage(@RequestBody MessageMainPageRequest mainPageRequest){
        return ResponseEntity.ok().body(messageService.findAllForMessageMain(mainPageRequest));
    }

    @GetMapping("/message-card/{id}")
    public ModelAndView messageCardPage(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("user/pages/message/message-card");
        modelAndView.addObject("id", id);
        messageService.changeCheck(id);
        return modelAndView;
    }

    @GetMapping("/get-message-by-id/{id}")
    public ResponseEntity getMessageById(@PathVariable Long id){
        try {
            return ResponseEntity.ok().body(messageService.findByIdForCard(id));
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().body("Message not found");
        }
    }


    @GetMapping("/get-message-for-header")
    public ResponseEntity getAllMessageForHeader(){
        try {
            return ResponseEntity.ok().body(messageService.findAllForMessageHeader());
        }
        catch (EntityNotFoundException e){
            return ResponseEntity.badRequest().build();
        }

    }

    @DeleteMapping("/delete-message/{id}")
    public ResponseEntity deleteMessage(@PathVariable Long id){
        messageService.deleteMessage(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/delete-messages")
    public ResponseEntity deleteMessages(@RequestParam List<Long> ids){
        for (Long id:ids) {
            messageService.deleteMessage(id);
        }
        return ResponseEntity.ok().build();
    }

}
