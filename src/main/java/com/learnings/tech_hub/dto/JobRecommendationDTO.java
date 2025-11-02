package com.learnings.tech_hub.dto;

import com.learnings.tech_hub.enums.RecommendationType;

public record JobRecommendationDTO(
        Long jobId,
        String title,
        String salaryRange,
        int matchPercentage,
        RecommendationType classification
) {
}
