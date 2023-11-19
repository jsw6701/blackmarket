package com.example.blackmarket.model;

import com.example.blackmarket.dto.ChatMessageDTO;
import com.example.blackmarket.dto.ChatMessageSaveDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Setter
@Getter
@Entity
public class ChatMessageEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "chat_room_id")
    private ChatRoomEntity chatRoomEntity;

    private String writer;

    private String message;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime sendDate;

    public static ChatMessageEntity toChatEntity(ChatMessageSaveDTO chatMessageSaveDTO, ChatRoomEntity chatRoomEntity){
        ChatMessageEntity chatMessageEntity = new ChatMessageEntity();

        chatMessageEntity.setChatRoomEntity(chatRoomEntity);
        chatMessageEntity.setMessage(chatMessageSaveDTO.getMessage());
        chatMessageEntity.setWriter(chatMessageSaveDTO.getWriter());

        return chatMessageEntity;
    }

    public ChatMessageDTO toChatMessageDTO(){
        ChatMessageDTO chatMessageDTO = new ChatMessageDTO();

        chatMessageDTO.setRoomId(this.chatRoomEntity.getRoomId());
        chatMessageDTO.setWriter(this.writer);
        chatMessageDTO.setMessage(this.message);
        chatMessageDTO.setSendDate(this.sendDate);

        return chatMessageDTO;
    }
}
