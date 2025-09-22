package com.learnings.tech_hub.dtos;

import com.learnings.tech_hub.enums.SkillLevel;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SkillDTO {
    @NotBlank
    private String name;
    private SkillLevel level;
    public SkillDTO(String name) {
        this.name = name;
    }
}
