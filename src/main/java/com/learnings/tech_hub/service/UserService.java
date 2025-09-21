package com.learnings.tech_hub.service;

import com.learnings.tech_hub.dtos.SkillDTO;
import com.learnings.tech_hub.dtos.UserDTO;
import com.learnings.tech_hub.entities.Skill;
import com.learnings.tech_hub.entities.User;
import com.learnings.tech_hub.entities.UserSkill;
import com.learnings.tech_hub.enums.UpsertMode;
import com.learnings.tech_hub.exceptions.UserAlreadyExistsException;
import com.learnings.tech_hub.exceptions.UserNotFoundException;
import com.learnings.tech_hub.mappers.UserMapper;
import com.learnings.tech_hub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserSkillService userSkillService;
    private final SkillService skillService;
    private final UserMapper userMapper;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) throws UserAlreadyExistsException {
        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email " + userDTO.getEmail() +" already exists");
        }
        User user = userMapper.toEntity(userDTO);
        user = userRepository.save(user);

        List<SkillDTO> skillDTOs = userDTO.getSkills();
        if (skillDTOs != null &&  !skillDTOs.isEmpty()) {
            // create new entities for not existing skills unless return the existing skills
            List<Skill> skillsEntities = skillService.saveOrGetSkills(skillDTOs);
            List<UserSkill> userSkills = userSkillService.saveOrUpdateUserSkills(user, skillDTOs, skillsEntities, UpsertMode.MERGE);
            user.setSkills(userSkills);
            userRepository.save(user);
        }
        return userMapper.toDTO(user);
    }

    public UserDTO getUserById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        return userMapper.toDTO(user);
    }

    public void deleteUserById(Long id) throws UserNotFoundException {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        userRepository.delete(user);
    }
}
