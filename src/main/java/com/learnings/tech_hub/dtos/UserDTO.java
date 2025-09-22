package com.learnings.tech_hub.dtos;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO {
    private Long id;

    @NotBlank(message = "Name should not be empty")
    private String name;

    @Email(message = "Email should be in correct format")
    private String email;

    @Min(value = 0, message = "Years of experience cannot be negative")
    @Max(value = 50, message = "Years of experience seems too high")
    private Integer yearsOfExperience;
    private List<SkillDTO> skills;
    private List<JobDTO> savedJobs;
}
