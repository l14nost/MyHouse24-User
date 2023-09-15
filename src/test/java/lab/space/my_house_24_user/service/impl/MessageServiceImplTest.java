package lab.space.my_house_24_user.service.impl;

import lab.space.my_house_24_user.entity.Message;
import lab.space.my_house_24_user.entity.Staff;
import lab.space.my_house_24_user.model.message.MessageMainPageRequest;
import lab.space.my_house_24_user.model.message.MessageResponseForCard;
import lab.space.my_house_24_user.model.message.MessageResponseForHeader;
import lab.space.my_house_24_user.model.message.MessageResponseForMain;
import lab.space.my_house_24_user.repository.MessageRepository;
import lab.space.my_house_24_user.service.UserService;
import lab.space.my_house_24_user.specification.MessageSpecification;
import lab.space.my_house_24_user.specification.MessageSpecificationForHeader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.time.*;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MessageServiceImplTest {
    @Mock
    private MessageRepository messageRepository;
    @Mock
    private UserService userService;
    @InjectMocks
    private MessageServiceImpl messageService;

    @Test
    void findAllForMessageMain() {
        List<Message> messageList = List.of(
                Message.builder().staff(Staff.builder().build()).sendDate(Instant.now()).build(),
                Message.builder().staff(Staff.builder().build()).sendDate(Instant.now()).build(),
                Message.builder().staff(Staff.builder().build()).sendDate(Instant.now()).build()
        );
        when(userService.getCurrentUser()).thenReturn(1L);
        MessageSpecification messageSpecification = MessageSpecification.builder().id(1L).mainPageRequest(new MessageMainPageRequest(1,"keyWord")).build();
        when(messageRepository.findAll(messageSpecification, PageRequest.of(1,10))).thenReturn(new PageImpl<>(messageList));
        Page<MessageResponseForMain> messageResponseForMains = messageService.findAllForMessageMain(new MessageMainPageRequest(1,"keyWord"));
        assertEquals(new PageImpl<>(messageList).getTotalElements(),messageResponseForMains.getTotalElements());

    }

    @Test
    void findAllForMessageHeader() {
        when(userService.getCurrentUser()).thenReturn(1L);
        MessageSpecificationForHeader messageSpecification = MessageSpecificationForHeader.builder().id(1L).build();
        when(messageRepository.findAll(messageSpecification)).thenReturn(List.of(
                Message.builder().build(),
                Message.builder().build(),
                Message.builder().build(),
                Message.builder().build()
        ));
        List<MessageResponseForHeader> messageResponseForHeaders = messageService.findAllForMessageHeader();
        assertEquals(4, messageResponseForHeaders.size());

    }

    @Test
    void changeCheck() {
        when(messageRepository.findById(1L)).thenReturn(Optional.of(Message.builder()
                .id(1L)
                .isCheck(false)
                .build()));
        messageService.changeCheck(1L);
        verify(messageRepository).save(Message.builder().id(1L).isCheck(true).build());
    }

    @Test
    void findByIdForCard() {
        when(messageRepository.findById(1L)).thenReturn(Optional.of(Message.builder()
                .id(1L)
                .isCheck(false)
                .staff(Staff.builder().build())
                .sendDate(LocalDateTime.of(2021, Month.APRIL,12, 12,20).atZone(ZoneId.systemDefault()).toInstant())
                .build()));
        MessageResponseForCard messageResponseForCard = messageService.findByIdForCard(1L);
        MessageResponseForCard messageResponseForCard1 =
                MessageResponseForCard.builder()
                        .id(1L)
                        .sendDate(LocalDateTime.of(2021, Month.APRIL,12,12,20))
                        .staffFullName("null null")
                        .build();
        assertEquals(messageResponseForCard1, messageResponseForCard);
    }

    @Test
    void deleteMessage() {
        messageService.deleteMessage(1L);
        verify(messageRepository,times(1)).deleteById(1L);
    }
}