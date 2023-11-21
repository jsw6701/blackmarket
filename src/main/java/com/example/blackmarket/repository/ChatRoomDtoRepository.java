package com.example.blackmarket.repository;

import com.example.blackmarket.dto.ChatRoomDTO;
import com.example.blackmarket.model.ChatRoomEntity;
import com.example.blackmarket.model.Post;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.*;
@Repository
public class ChatRoomDtoRepository {

    private Map<String, ChatRoomDTO> chatRoomDTOMap;

    private final ChatRoomRepository chatRoomRepository;

    public ChatRoomDtoRepository(ChatRoomRepository chatRoomRepository) {
        this.chatRoomRepository = chatRoomRepository;
    }

    @PostConstruct
    private void init(){
        chatRoomDTOMap = new LinkedHashMap<>();
    }

    public List<ChatRoomDTO> findAllRooms(){
        //채팅방 생성 순서 최근 순으로 반환
        List<ChatRoomDTO> result = new ArrayList<>(chatRoomDTOMap.values());
        Collections.reverse(result);

        return result;
    }

    public ChatRoomDTO findRoomById(String id){
        return chatRoomDTOMap.get(id);
    }

    public ChatRoomDTO findRoomByName(String name){
        return chatRoomDTOMap.get(name);
    }

    public String createChatRoomDTO(String name, Post post){
        ChatRoomDTO room = ChatRoomDTO.create(name);
        chatRoomDTOMap.put(room.getRoomId(), room);
        ChatRoomEntity chatRoomEntity = ChatRoomEntity.toChatRoomEntity(room.getName(), room.getRoomId(), post);
        chatRoomRepository.save(chatRoomEntity);
        return name;
    }
    public ChatRoomDTO createChatRoomDTO44(String name, Post post){
        ChatRoomDTO room = ChatRoomDTO.create(name);
        chatRoomDTOMap.put(room.getRoomId(), room);
        ChatRoomEntity chatRoomEntity = ChatRoomEntity.toChatRoomEntity(room.getName(), room.getRoomId(), post);
        chatRoomRepository.save(chatRoomEntity);
        return room;
    }
}
