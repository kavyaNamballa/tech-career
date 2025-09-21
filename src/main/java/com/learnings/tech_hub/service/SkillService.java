package com.learnings.tech_hub.service;

import com.learnings.tech_hub.dtos.SkillDTO;
import com.learnings.tech_hub.entities.Skill;
import com.learnings.tech_hub.repository.SkillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SkillService {
    private final SkillRepository skillRepository;

    @Transactional
    public List<Skill> saveOrGetSkills(List<SkillDTO> skillDTOs) {
        if(skillDTOs == null || skillDTOs.isEmpty()) return Collections.emptyList();

        // normalize names and preserve order
        List<String> normalizedNames = skillDTOs.stream()
                .map(SkillDTO::getName)
                .filter(Objects::nonNull)
                .map(String::trim)
                .distinct()
                .toList();
        List<Skill> existingSkills = skillRepository.findByNameInIgnoreCase(normalizedNames);
        List<Skill> totalSkills = new ArrayList<>();
        List<Skill> toSave = getToBeSavedSkills(existingSkills, normalizedNames, totalSkills);
        if(!toSave.isEmpty()) {
            totalSkills.addAll(skillRepository.saveAll(toSave));
        }
        return totalSkills;
    }

    private List<Skill> getToBeSavedSkills(List<Skill> existingSkills, List<String> skills, List<Skill> totalSkills) {
        if (existingSkills == null || existingSkills.isEmpty()) {
            return skills.stream().map(Skill::new).collect(Collectors.toList());
        }
        // here I can write skill -> skill, but we can make use of function.identity to return the same input in lambda function
        Map<String, Skill> existingMap = existingSkills.stream()
                .collect(Collectors.toMap(skill -> skill.getName().toLowerCase(), Function.identity()));
        List<Skill> toSave = new ArrayList<>();
        for (String name : skills) {
            String lowerCaseName = name.toLowerCase();
            if (!existingMap.containsKey(lowerCaseName)) {
                Skill s = new Skill();
                s.setName(name);
                toSave.add(s);
            } else totalSkills.add(existingMap.get(lowerCaseName));
        }
        return toSave;
    }
}
