package com.example.blackmarket.service;

import com.example.blackmarket.dto.requeset.UserUpdateDto;
import com.example.blackmarket.dto.response.UserDto;
import com.example.blackmarket.model.User;
import com.example.blackmarket.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto findById(Long id){
        return new UserDto(userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다.")));
    }

    public UserDto findByEmail(String email){
        return new UserDto(userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("해당 유저가 없습니다.")));
    }

    public UserDto update(UserUpdateDto userUpdateDto, User user){

        user.setPhoneNumber(userUpdateDto.getPhoneNumber());
        user.setNickname(userUpdateDto.getNickname());
        user.setAccount(userUpdateDto.getAccount());

        userRepository.save(user);

        return new UserDto(user);
    }

    public void deleteById(Long id){
        userRepository.deleteById(id);
    }
}
