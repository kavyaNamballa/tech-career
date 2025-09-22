package com.learnings.tech_hub.service;

import com.learnings.tech_hub.dtos.SkillDTO;
import com.learnings.tech_hub.entities.Skill;
import com.learnings.tech_hub.entities.User;
import com.learnings.tech_hub.entities.UserSkill;
import com.learnings.tech_hub.enums.SkillLevel;
import com.learnings.tech_hub.enums.UpsertMode;
import com.learnings.tech_hub.exceptions.ResourceNotFoundException;
import com.learnings.tech_hub.mappers.UserMapper;
import com.learnings.tech_hub.repository.UserRepository;
import com.learnings.tech_hub.repository.UserSkillRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserSkillService {
    private final UserSkillRepository userSkillRepository;
    private final SkillService skillService;
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Transactional
    public List<SkillDTO> upsertUserSkills(Long userId, List<SkillDTO> skillDTOS, UpsertMode mode) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        List<Skill> skillEntities = skillService.saveOrGetSkills(skillDTOS);
        log.info("Upserting skills for userId: {} of {}", userId, skillEntities.size());
        List<UserSkill> userSkills = saveOrUpdateUserSkills(user, skillDTOS, skillEntities, mode);
        return userMapper.mapUserSkills(userSkills);
    }

    public List<SkillDTO> getUserSkills(Long userId) throws ResourceNotFoundException {
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        List<UserSkill> userSkills = userSkillRepository.findAllByUserId(userId);
        return userMapper.mapUserSkills(userSkills);
    }
    @Transactional
    public List<UserSkill> saveOrUpdateUserSkills(User user, List<SkillDTO> skills, List<Skill> skillEntities, UpsertMode upsertMode) {
        List<UserSkill> existingUserSkills = userSkillRepository.findAllByUserId(user.getId());
        Map<String, Skill> skillByName = skillEntities.stream()
                .collect(Collectors.toMap(s -> s.getName().toLowerCase(), Function.identity(), (a,b) -> a));
        if (existingUserSkills == null || existingUserSkills.isEmpty()) {
            List<UserSkill> toSave = getToBeSavedUserSkills(user, skills, skillByName, null);
            return userSkillRepository.saveAll(toSave);
        } else {
            Map<Long, UserSkill> existingBySkillId = existingUserSkills.stream()
                    .collect(Collectors.toMap(us -> us.getSkill().getId(), Function.identity(), (a,b) -> a));
            Set<Long> incomingSkillIds = skillEntities.stream().map(Skill::getId).collect(Collectors.toSet());
            List<UserSkill> toCreate = getToBeSavedUserSkills(user, skills, skillByName, existingBySkillId);
            if (upsertMode == UpsertMode.REPLACE) {
                Set<Long> existingSkillIds = existingUserSkills.stream()
                        .map(us -> us.getSkill().getId()).collect(Collectors.toSet());
                Set<Long> toDeleteIds = existingSkillIds.stream()
                        .filter(id -> !incomingSkillIds.contains(id))
                        .collect(Collectors.toSet());
                if (!toDeleteIds.isEmpty()) {
                    userSkillRepository.deleteByUserIdAndSkillIdIn(user.getId(), toDeleteIds);
                }
            }
            if (!toCreate.isEmpty()) {
                userSkillRepository.saveAll(toCreate);
            }
            return userSkillRepository.findAllByUserId(user.getId());
        }
    }

    private List<UserSkill> getToBeSavedUserSkills(User user, List<SkillDTO> skillDTOS,
                                                   Map<String, Skill> skillByName, Map<Long, UserSkill> existingBySkillId) {
        List<UserSkill> toSave = new ArrayList<>();
        // track processed normalized names to avoid duplicates in incoming list
        Set<String> seen = new HashSet<>();
        for (SkillDTO dto : skillDTOS) {
            String normalized = dto.getName().trim().toLowerCase();
            if (!seen.add(normalized)) {
                continue;
            }
            Skill skill = skillByName.get(normalized);
            SkillLevel targetLevel = dto.getLevel() == null ? SkillLevel.BEGINNER : dto.getLevel();
            UserSkill existing = existingBySkillId != null ? existingBySkillId.get(skill.getId()) : null;
            if (existing != null) {
                // update level if changed
                if (existing.getLevel() != targetLevel) {
                    existing.setLevel(targetLevel);
                    // managed entity will be flushed at commit
                }
            } else {
                // not existing — create later
                UserSkill us = new UserSkill(null, user, skill, targetLevel);
                toSave.add(us);
            }
        }
        return toSave;
    }
}
