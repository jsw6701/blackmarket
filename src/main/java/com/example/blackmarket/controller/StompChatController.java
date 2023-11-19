package com.example.blackmarket.controller;

import com.example.blackmarket.dto.ChatMessageDTO;
import com.example.blackmarket.dto.ChatMessageSaveDTO;
import com.example.blackmarket.model.ChatMessageEntity;
import com.example.blackmarket.model.ChatRoomEntity;
import com.example.blackmarket.repository.ChatMessageRepository;
import com.example.blackmarket.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class StompChatController {

    private final SimpMessagingTemplate template;

    private final ChatRoomRepository chatRoomRepository;

    private final ChatMessageRepository chatMessageRepository;

    @MessageMapping(value = "/chat/enter")
    public void enter(ChatMessageDTO message){
        message.setMessage(message.getWriter() + "님이 입장하셨습니다.");
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);

        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByRoomId(message.getRoomId());
        ChatMessageSaveDTO chatMessageSaveDTO = new ChatMessageSaveDTO(message.getRoomId(), message.getWriter(), message.getMessage());
        chatMessageRepository.save(ChatMessageEntity.toChatEntity(chatMessageSaveDTO, chatRoomEntity));
    }

    @MessageMapping(value = "/chat/message")
    public void message(ChatMessageDTO message){
        template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
        ChatRoomEntity chatRoomEntity = chatRoomRepository.findByRoomId(message.getRoomId());
        ChatMessageSaveDTO chatMessageSaveDTO = new ChatMessageSaveDTO(message.getRoomId(), message.getWriter(), message.getMessage());
        chatMessageRepository.save(ChatMessageEntity.toChatEntity(chatMessageSaveDTO, chatRoomEntity));
    }

    @GetMapping(value = "/chat/room/history")
    public ResponseEntity<?> history(@RequestParam String roomId){
        List<ChatMessageEntity> messages = chatMessageRepository.findAllByChatRoomEntityRoomId(roomId);
        // Assuming you have a method to convert entities to DTOs
        List<ChatMessageDTO> dtos = messages.stream().map(ChatMessageEntity::toChatMessageDTO).collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }

}
