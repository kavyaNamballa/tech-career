package com.learnings.tech_hub.specification;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class JobSearchCriteria {
    private String titleContains;
    private Double salaryInLPA;
    private Integer minExperienceLessThanOrEqual;
    private Integer minExperienceGreaterThanOrEqual;
}
