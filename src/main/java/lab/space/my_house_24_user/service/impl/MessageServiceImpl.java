package lab.space.my_house_24_user.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lab.space.my_house_24_user.entity.Message;
import lab.space.my_house_24_user.mapper.MessageMapper;
import lab.space.my_house_24_user.model.message.MessageMainPageRequest;
import lab.space.my_house_24_user.model.message.MessageResponseForCard;
import lab.space.my_house_24_user.model.message.MessageResponseForHeader;
import lab.space.my_house_24_user.model.message.MessageResponseForMain;
import lab.space.my_house_24_user.repository.MessageRepository;
import lab.space.my_house_24_user.service.MessageService;
import lab.space.my_house_24_user.service.UserService;
import lab.space.my_house_24_user.specification.MessageSpecification;
import lab.space.my_house_24_user.specification.MessageSpecificationForHeader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;
    private final UserService userService;
    @Override
    public Page<MessageResponseForMain> findAllForMessageMain(MessageMainPageRequest mainPageRequest) {
        log.info("Try to find all message dto for main page");
        MessageSpecification messageSpecification = MessageSpecification.builder().mainPageRequest(mainPageRequest).id(userService.getCurrentUser()).build();
        return messageRepository.findAll(messageSpecification, PageRequest.of(mainPageRequest.page(),10)).map(MessageMapper::entityToDtoForMain);
    }

    @Override
    public List<MessageResponseForHeader> findAllForMessageHeader() {
        log.info("Try to find all message dto for header");
        MessageSpecificationForHeader messageSpecification = MessageSpecificationForHeader.builder().id(userService.getCurrentUser()).build();
        return messageRepository.findAll(messageSpecification).stream().map(MessageMapper::entityToDtoForHeader).toList();
    }

    public void changeCheck(Long id){
        log.info("Try to change check");
        Message message = messageRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Message by id "+id+" is not found"));
        message.setIsCheck(true);
        messageRepository.save(message);
        log.info("Change check status");
    }

    @Override
    public MessageResponseForCard findByIdForCard(Long id) {
        log.info("Try to find message by id: "+id+" dto for card page");
        return MessageMapper.entityToDtoForCard(messageRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Message by id "+id+" is not found")));
    }

    @Override
    public void deleteMessage(Long id) {
        log.info("Try to delete message by id: "+id);
        messageRepository.deleteById(id);
    }
}
