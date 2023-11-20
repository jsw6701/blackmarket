package com.example.blackmarket.model;

import com.example.blackmarket.dto.response.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String roomId;

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    private List<ChatMessageEntity> messages;

    @ManyToOne(cascade = CascadeType.ALL)
    private Post post;

    public static ChatRoomEntity toChatRoomEntity(String name, String roomId, Post post){
        ChatRoomEntity chatRoomEntity = new ChatRoomEntity();

        chatRoomEntity.setName(name);
        chatRoomEntity.setRoomId(roomId);
        chatRoomEntity.setPost(post);

        return chatRoomEntity;
    }
}
