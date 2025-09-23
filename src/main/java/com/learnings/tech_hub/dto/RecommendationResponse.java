package com.learnings.tech_hub.dto;

import java.util.List;

public record RecommendationResponse(
        List<JobRecommendationDTO> rolesYouCanApplyNow,
        List<JobRecommendationDTO> rolesToAimFor,
        List<SkillGapDTO> skillGapPlan
) {}
