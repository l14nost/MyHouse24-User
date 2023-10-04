package lab.space.my_house_24_user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "house_id")
    private House house;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "chat")
    private List<ChatMessage> messageList = new ArrayList<>();

    public void addMessage(ChatMessage chatMessage){
        chatMessage.setChat(this);
        messageList.add(chatMessage);
    }
}
