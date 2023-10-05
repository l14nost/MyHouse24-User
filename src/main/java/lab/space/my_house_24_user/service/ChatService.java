package lab.space.my_house_24_user.service;

import lab.space.my_house_24_user.entity.Chat;
import lab.space.my_house_24_user.model.chat.ChatMessageFileRequest;
import lab.space.my_house_24_user.model.chat.ChatMessageRequest;
import lab.space.my_house_24_user.model.chat.ChatMessageResponse;

import java.util.List;

public interface ChatService {
    void sendFile(ChatMessageFileRequest chatMessageFileRequest);
    void sendMessage(ChatMessageRequest chatMessageRequest);
    Chat findById(Long id);

    List<ChatMessageResponse> findMessageByChat(Long id);
}
