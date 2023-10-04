package lab.space.my_house_24_user.controller;

import lab.space.my_house_24_user.model.chat.ChatMessageFileRequest;
import lab.space.my_house_24_user.model.chat.ChatMessageRequest;
import lab.space.my_house_24_user.service.ChatService;
import lab.space.my_house_24_user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final UserService userService;
    @GetMapping("/{id}")
    public ModelAndView chat(@PathVariable Long id){
        ModelAndView modelAndView = new ModelAndView("user/pages/chat/chat");
        modelAndView.addObject("id", id);
        modelAndView.addObject("currentUserId", userService.getCurrentUser());
        return modelAndView;
    }
    @GetMapping("/get-all-message-by-chat/{id}")
    public ResponseEntity allChat(@PathVariable Long id){
        return ResponseEntity.ok(chatService.findMessageByChat(id));
    }

    @PostMapping("/send-file")
    public ResponseEntity sendFile(@ModelAttribute ChatMessageFileRequest chatMessageFileRequest){
        chatService.sendFile(chatMessageFileRequest);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/send-message")
    public ResponseEntity sendMessage(@RequestBody ChatMessageRequest chatMessageRequest){
        chatService.sendMessage(chatMessageRequest);
        return ResponseEntity.ok().build();
    }

    @MessageMapping("/main")
    @SendTo("/topic/refresh")
    public String change(){
        return "change";
    }
}
