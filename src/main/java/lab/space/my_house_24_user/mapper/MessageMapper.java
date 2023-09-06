package lab.space.my_house_24_user.mapper;

import lab.space.my_house_24_user.entity.Message;
import lab.space.my_house_24_user.model.message.MessageResponseForCard;
import lab.space.my_house_24_user.model.message.MessageResponseForHeader;
import lab.space.my_house_24_user.model.message.MessageResponseForMain;
import org.springframework.context.i18n.LocaleContextHolder;

import java.time.ZoneId;
import java.util.Locale;

public class MessageMapper {

    public static MessageResponseForMain entityToDtoForMain(Message message){
        return MessageResponseForMain.builder()
                .id(message.getId())
                .from(message.getStaff().getLastname()+" "+message.getStaff().getFirstname())
                .text("<b>"+message.getTitle()+"</b>"+"-"+message.getDescription())
                .sendDate(message.getSendDate().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .check(message.getIsCheck())
                .build();
    }

    public static MessageResponseForHeader entityToDtoForHeader(Message message){
        return MessageResponseForHeader.builder()
                .id(message.getId())
                .title(message.getTitle())
                .build();
    }

    public static MessageResponseForCard entityToDtoForCard(Message message){
        return MessageResponseForCard.builder()
                .title(message.getTitle())
                .message(message.getDescriptionStyle())
                .sendDate(message.getSendDate().atZone(ZoneId.systemDefault()).toLocalDateTime())
                .staffFullName(message.getStaff().getLastname()+" "+message.getStaff().getFirstname())
                .id(message.getId())
                .build();
    }
}
