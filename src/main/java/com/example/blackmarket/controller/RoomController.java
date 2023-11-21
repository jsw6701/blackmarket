package com.example.blackmarket.controller;

import com.example.blackmarket.dto.ChatRoomDTO;
import com.example.blackmarket.dto.response.PostDto;
import com.example.blackmarket.model.ChatRoomEntity;
import com.example.blackmarket.model.Post;
import com.example.blackmarket.repository.ChatRoomDtoRepository;
import com.example.blackmarket.repository.ChatRoomRepository;
import com.example.blackmarket.security.CurrentUser;
import com.example.blackmarket.security.UserPrincipal;
import com.example.blackmarket.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@RequestMapping(value = "/chat")
@Log4j2
public class RoomController {

    private final ChatRoomDtoRepository repository;

    private final ChatRoomRepository chatRoomRepository;

    private final PostService postService;

    //채팅방 목록 조회
    @GetMapping(value = "/rooms")
    public ModelAndView rooms(@CurrentUser UserPrincipal userPrincipal){
        boolean loginflag = false;
        if(userPrincipal != null){
            loginflag = true;
        }
        log.info("# All Chat Rooms");
        ModelAndView mv = new ModelAndView("chat/rooms");

        mv.addObject("list", chatRoomRepository.findAll());
        mv.addObject("loginflag",loginflag);
        return mv;
    }

    //채팅방 개설
    @GetMapping(value = "/start")
    public String createRoom(@RequestParam Long postId, RedirectAttributes rttr,@CurrentUser UserPrincipal userPrincipal){

        Post post = postService.findById(postId);
        String name = post.getId()+"||"+userPrincipal.getId()+"||"+post.getUser().getId();

        log.info("# Create Chat Room , name: " + name);

        ChatRoomDTO room = null;
        ChatRoomEntity roomEntity = chatRoomRepository.findByName(name);
        if(roomEntity == null){
            room = repository.createChatRoomDTO44(name,post);
            return "redirect:/chat/room?roomId="+room.getRoomId();
        }else{
            return "redirect:/chat/room?roomId="+roomEntity.getRoomId();
        }
    }


    //채팅방 개설
    @PostMapping(value = "/room")
    public String create(@RequestParam String name, @RequestParam Long postId, RedirectAttributes rttr){

        Post post = postService.findById(postId);
        log.info("# Create Chat Room , name: " + name);
        rttr.addFlashAttribute("roomName", repository.createChatRoomDTO(name, post));
        return "redirect:/chat/rooms";
    }

    //채팅방 조회
    @GetMapping("/room")
    public void getRoom(String roomId, Model model, @CurrentUser UserPrincipal userPrincipal){
        boolean loginflag = false;
        if(userPrincipal != null){
            loginflag = true;
        }
        log.info("# get Chat Room, roomID : " + roomId);
        model.addAttribute("list", chatRoomRepository.findAll());
        model.addAttribute("loginflag",loginflag);
        model.addAttribute("room", chatRoomRepository.findByRoomId(roomId));
    }


}