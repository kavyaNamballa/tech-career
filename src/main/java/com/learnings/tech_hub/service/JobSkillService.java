package com.learnings.tech_hub.service;

import com.learnings.tech_hub.dtos.SkillDTO;
import com.learnings.tech_hub.entities.*;
import com.learnings.tech_hub.enums.SkillLevel;
import com.learnings.tech_hub.repository.JobSkillRepository;
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
public class JobSkillService {
    private final JobSkillRepository jobSkillRepository;

    @Transactional
    public List<JobSkill> saveJobSkills(Job job, List<SkillDTO> skills, List<Skill> skillEntities) {
        Map<String, SkillDTO> dtoByName = skills.stream()
                .collect(Collectors.toMap(s -> s.getName().toLowerCase(), Function.identity(), (a,b) -> a));
        List<JobSkill> jobSkills = new ArrayList<>();
        for (Skill skill : skillEntities) {
            SkillDTO skillDTO = dtoByName.get(skill.getName().toLowerCase());
            SkillLevel targetLevel =  skillDTO.getLevel()  == null ? SkillLevel.BEGINNER : skillDTO.getLevel();
            JobSkill jobSkill = new JobSkill(job, skill, targetLevel);
            jobSkills.add(jobSkill);
        }
        if (!jobSkills.isEmpty()) {
            jobSkillRepository.saveAll(jobSkills);
        }
        return jobSkillRepository.findAllByJobId(job.getId());
    }
}
