package com.example.blackmarket.repository;


import com.example.blackmarket.model.ChatRoomEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoomEntity, Long> {

    ChatRoomEntity findByRoomId(String roomId);

    ChatRoomEntity findByName(String name);
}
