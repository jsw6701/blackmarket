package com.example.blackmarket.repository;


import com.example.blackmarket.model.ChatMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessageEntity, Long> {

    List<ChatMessageEntity> findAllByChatRoomEntityRoomId(String roomId);
}
