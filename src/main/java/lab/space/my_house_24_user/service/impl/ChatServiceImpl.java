package lab.space.my_house_24_user.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.entity.Chat;
import lab.space.my_house_24_user.entity.ChatMessage;
import lab.space.my_house_24_user.entity.User;
import lab.space.my_house_24_user.enums.MessageType;
import lab.space.my_house_24_user.mapper.ChatMessageMapper;
import lab.space.my_house_24_user.model.chat.ChatMessageFileRequest;
import lab.space.my_house_24_user.model.chat.ChatMessageRequest;
import lab.space.my_house_24_user.model.chat.ChatMessageResponse;
import lab.space.my_house_24_user.repository.ChatRepository;
import lab.space.my_house_24_user.service.ChatService;
import lab.space.my_house_24_user.service.UserService;
import lab.space.my_house_24_user.util.FileHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepository chatRepository;
    private final UserService userService;

    @Override
    public void sendFile(ChatMessageFileRequest chatMessageFileRequest) {
        String message="";
        String filename = "";
        MessageType type = MessageType.IMG;
        if (!chatMessageFileRequest.file().isEmpty()){
            message = FileHandler.saveFile(chatMessageFileRequest.file());
            if (!isFileExtensionImg(chatMessageFileRequest.file())){
                type = MessageType.FILE;
                filename = chatMessageFileRequest.file().getOriginalFilename();
            }
        }
        ChatMessage chatMessage = ChatMessage.builder().message(message).type(type).user(User.builder().id(userService.getCurrentUser()).build()).filename(filename).date(Instant.now()).build();
        Chat chat = findById(chatMessageFileRequest.id());
        chat.addMessage(chatMessage);
        chatRepository.save(chat);
    }

    private boolean isFileExtensionImg(MultipartFile file) {
        String[] allowedExtensions = {"jpg", "png", "jpeg"};
        String originalFilename = file.getOriginalFilename();
        if (originalFilename.length()>3) {
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
            for (String allowedExtension : allowedExtensions) {
                if (allowedExtension.equalsIgnoreCase(fileExtension)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void sendMessage(ChatMessageRequest chatMessageRequest) {
        ChatMessage chatMessage = ChatMessage.builder().message(chatMessageRequest.message()).type(MessageType.TEXT).user(User.builder().id(userService.getCurrentUser()).build()).date(Instant.now()).build();
        Chat chat = findById(chatMessageRequest.id());
        chat.addMessage(chatMessage);
        chatRepository.save(chat);
    }

    @Override
    public Chat findById(Long id) {
        return chatRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Chat by id: "+id+" not found"));
    }

    @Override
    public List<ChatMessageResponse> findMessageByChat(Long id) {
        return findById(id).getMessageList().stream().map(ChatMessageMapper::entityToResponseForMain).toList();
    }
}
