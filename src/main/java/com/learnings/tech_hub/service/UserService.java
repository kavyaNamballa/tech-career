package com.learnings.tech_hub.service;

import com.learnings.tech_hub.dtos.UserDTO;
import com.learnings.tech_hub.entities.User;
import com.learnings.tech_hub.exceptions.UserAlreadyExistsException;
import com.learnings.tech_hub.exceptions.UserNotFoundException;
import com.learnings.tech_hub.mappers.UserMapper;
import com.learnings.tech_hub.repository.SkillRepository;
import com.learnings.tech_hub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) throws UserAlreadyExistsException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + userDTO.getEmail() +" already exists");
        }
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);
        return userMapper.toDTO(user);
    }

    public UserDTO getUserById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return userMapper.toDTO(user);
    }
}
