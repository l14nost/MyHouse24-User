package lab.space.my_house_24_user.mapper;

import lab.space.my_house_24_user.entity.ChatMessage;
import lab.space.my_house_24_user.model.chat.ChatMessageResponse;

import java.time.ZoneId;

public class ChatMessageMapper {

    public static ChatMessageResponse entityToResponseForMain(ChatMessage chatMessage){
        return ChatMessageResponse.builder()
                .message(chatMessage.getMessage())
                .filename(chatMessage.getFilename())
                .type(chatMessage.getType())
                .userAvatar(chatMessage.getUser().getFilename())
                .userId(chatMessage.getUser().getId())
                .userName(chatMessage.getUser().getLastname()+" "+chatMessage.getUser().getFirstname().charAt(0)+".")
                .id(chatMessage.getId())
                .time(chatMessage.getDate().atZone(ZoneId.systemDefault()).toLocalDateTime().getHour()+":"+chatMessage.getDate().atZone(ZoneId.systemDefault()).toLocalDateTime().getMinute())
                .build();
    }
}
