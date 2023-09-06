package lab.space.my_house_24_user.controller;

import lab.space.my_house_24_user.model.message.MessageMainPageRequest;
import lab.space.my_house_24_user.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/messages")
public class MessageController {
    private final MessageService messageService;
    @GetMapping("")
    public String messagePage(){
        return "/user/pages/message/message-main";
    }


    @PostMapping("/get-all-message")
    public ResponseEntity getAllMessage(@RequestBody MessageMainPageRequest mainPageRequest){
        return ResponseEntity.ok().body(messageService.findAllForMessageMain(mainPageRequest));
    }

    @GetMapping("/message-card/{id}")
    public String messageCardPage(@PathVariable Long id, Model model){
        model.addAttribute("id", id);
        messageService.changeCheck(id);
        return "/user/pages/message/message-card";
    }

    @GetMapping("/get-message-by-id/{id}")
    public ResponseEntity getMessageById(@PathVariable Long id){
        return ResponseEntity.ok().body(messageService.findByIdForCard(id));
    }


    @GetMapping("/get-message-for-header")
    public ResponseEntity getAllMessageForHeader(){
        return ResponseEntity.ok().body(messageService.findAllForMessageHeader());
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