package com.learnings.tech_hub.util;

import com.learnings.tech_hub.constant.Messages;
import com.learnings.tech_hub.dto.SkillGapDTO;
import com.learnings.tech_hub.entity.Skill;
import com.learnings.tech_hub.enums.RecommendationType;
import com.learnings.tech_hub.enums.SkillLevel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RecommendationUtil {

    public double addSkillDiff(Skill skill, SkillLevel level, SkillLevel reqLevel, List<SkillGapDTO> missing) {
        if(level==null) {
            missing.add(new SkillGapDTO(skill.getName(), SkillLevel.NONE, reqLevel, suggestionFor(reqLevel)));
            return 0.0;
        } else if (level.ordinal() >= reqLevel.ordinal()) {
            return 1.0;
        } else {
            missing.add(new SkillGapDTO(skill.getName(), level, reqLevel, suggestionFor(level)));
            return 0.5;
        }
    }

    public String suggestionFor(SkillLevel level) {
        switch (level) {
            case EXPERT:
                return Messages.EXPERT_SUGGESTION_MESSAGE;
            case INTERMEDIATE:
                return Messages.INTERMEDIATE_SUGGESTION_MESSAGE;
            case BEGINNER:
            default:
                return Messages.BEGINNER_SUGGESTION_MESSAGE;
        }
    }

    public int getScore(double score) {
        return (int)Math.round(score * 100);
    }

    public RecommendationType getRecommendationType(double score) {
        int roundedScore = getScore(score);
        return (roundedScore >= 70 ) ? RecommendationType.APPLY_NOW :
                (roundedScore >= 40 ? RecommendationType.AIM_FOR : RecommendationType.LOW_MATCH);
    }

    public boolean isRecommendToApplyNow(double score) {
        return getRecommendationType(score) == RecommendationType.APPLY_NOW;
    }

    public boolean isRecommendToAimFor(double score) {
        return getRecommendationType(score) == RecommendationType.AIM_FOR;
    }

}
