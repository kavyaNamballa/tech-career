package com.learnings.tech_hub.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class JobDTO {
    private Long id;
    @NotBlank(message = "Job Title should not be empty")
    private String title;

    @Pattern(regexp = "^\\d+(?:\\.\\d+)?-\\d+(?:\\.\\d+)? LPA$", message = "Salary range must be in format: '13-14 LPA' or '13.2-14.5 LPA'")
    private String salaryRangeInLPA;

    @Min(value = 0, message = "Years of experience cannot be negative")
    @Max(value = 50, message = "Years of experience seems too high")
    private Integer minExperience;
    @NotEmpty(message = "Please provide required skills to the job")
    private List<SkillDTO> skills;
}
