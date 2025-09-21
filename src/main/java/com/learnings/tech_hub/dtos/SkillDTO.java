package com.learnings.tech_hub.dtos;

import com.learnings.tech_hub.entities.Skill;
import com.learnings.tech_hub.enums.SkillLevel;
import lombok.Data;

@Data
public class SkillDTO {
    private Skill skill;
    private SkillLevel level;
}
