package com.learnings.tech_hub.dtos;

import com.learnings.tech_hub.enums.SkillLevel;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SkillDTO {
    @NotBlank
    private String name;
    private SkillLevel level;
    public SkillDTO(String name) {
        this.name = name;
    }
}
