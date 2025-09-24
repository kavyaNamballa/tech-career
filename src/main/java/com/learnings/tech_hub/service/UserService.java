package com.learnings.tech_hub.service;

import com.learnings.tech_hub.dto.RecommendationResponse;
import com.learnings.tech_hub.dto.SkillDTO;
import com.learnings.tech_hub.dto.UserDTO;
import com.learnings.tech_hub.entity.Skill;
import com.learnings.tech_hub.entity.User;
import com.learnings.tech_hub.entity.UserSkill;
import com.learnings.tech_hub.enums.UpsertMode;
import com.learnings.tech_hub.exception.UserAlreadyExistsException;
import com.learnings.tech_hub.exception.ResourceNotFoundException;
import com.learnings.tech_hub.mapper.UserMapper;
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
    private final RecommendationService recommendationService;

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

    public UserDTO getUserById(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User not found: "+id));
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        return userMapper.toDTO(user);
    }

    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDTOs(users);
    }

    public void deleteUserById(Long id) throws ResourceNotFoundException {
        User user = userRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("User not found: "+id));
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        userRepository.delete(user);
    }

    public RecommendationResponse getRecommendations(Long id, int topN) throws ResourceNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        return recommendationService.recommendForUser(user, topN);
    }
}
