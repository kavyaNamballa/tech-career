package com.learnings.tech_hub.dtos;

import lombok.Data;

import java.util.List;

@Data
public class JobDTO {
    private Long id;
    private String title;
    private String salaryRangeInLPA;
    private String minExperience;
    private List<SkillDTO> skills;
}
