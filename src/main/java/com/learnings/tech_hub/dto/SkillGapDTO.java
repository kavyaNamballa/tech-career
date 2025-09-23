package com.learnings.tech_hub.dto;

import com.learnings.tech_hub.enums.SkillLevel;

public record SkillGapDTO(
        String skill,
        SkillLevel currentLevel,
        SkillLevel requiredLevel,
        String suggestion
) {
}
