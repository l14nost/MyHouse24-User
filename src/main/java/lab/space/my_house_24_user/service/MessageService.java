package lab.space.my_house_24_user.service;

import lab.space.my_house_24_user.model.message.MessageMainPageRequest;
import lab.space.my_house_24_user.model.message.MessageResponseForCard;
import lab.space.my_house_24_user.model.message.MessageResponseForHeader;
import lab.space.my_house_24_user.model.message.MessageResponseForMain;
import org.springframework.data.domain.Page;

import java.util.List;

public interface MessageService {
    Page<MessageResponseForMain> findAllForMessageMain(MessageMainPageRequest mainPageRequest);
    List<MessageResponseForHeader> findAllForMessageHeader();
    MessageResponseForCard findByIdForCard(Long id);
    void deleteMessage(Long idList);
    void changeCheck(Long id);
}
