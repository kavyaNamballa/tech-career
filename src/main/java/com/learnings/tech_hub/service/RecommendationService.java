package com.learnings.tech_hub.service;

import com.learnings.tech_hub.constant.Messages;
import com.learnings.tech_hub.dto.JobRecommendationDTO;
import com.learnings.tech_hub.dto.RecommendationResponse;
import com.learnings.tech_hub.dto.SkillGapDTO;
import com.learnings.tech_hub.entity.*;
import com.learnings.tech_hub.enums.SkillLevel;
import com.learnings.tech_hub.exception.ResourceNotFoundException;
import com.learnings.tech_hub.repository.JobRepository;
import com.learnings.tech_hub.util.RecommendationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecommendationService {
    private final JobRepository jobRepository;
    private final RecommendationUtil recommendationUtil;

    private final double SKILL_FACTOR = 0.75;
    private final double EXP_FACTOR = 0.25;

    public RecommendationResponse recommendForUser(User user, int topN) throws ResourceNotFoundException {
        var userSkills = user.getSkills();
        if (userSkills == null || userSkills.isEmpty()) {
            throw new ResourceNotFoundException(Messages.NO_SKILLS);
        }
        List<Job> jobs = jobRepository.findAll();
        if (jobs.isEmpty()) {
            throw new ResourceNotFoundException(Messages.NO_JOBS_AVAILABLE);
        }
        int userExp = user.getYearsOfExperience();
        Map<String, SkillLevel> userSkillMap = userSkills.stream()
                .collect(Collectors.toMap(us -> us.getSkill().getName().trim().toLowerCase(), UserSkill::getLevel));

        List<JobScore> jobScores = jobs.stream()
                .map(job -> computeJobScore(job, userExp, userSkillMap))
                .sorted(Comparator.comparingDouble(JobScore::score).reversed())
                .toList();

        List<JobRecommendationDTO> applyNow = jobScores.stream()
                .filter(dto -> recommendationUtil.isRecommendToApplyNow(dto.score()))
                .limit(topN)
                .map(this::toDto).toList();

        List<JobRecommendationDTO> aimFor = jobScores.stream()
                .filter(dto -> recommendationUtil.isRecommendToAimFor(dto.score()))
                .limit(topN)
                .map(this::toDto).toList();

        List<SkillGapDTO> skillGap = buildSkillGapPlan(jobScores, topN);
        return new RecommendationResponse(applyNow, aimFor, skillGap);
    }

    private JobScore computeJobScore(Job job, int userExp, Map<String, SkillLevel> userSkillMap) {
        List<JobSkill> requiredSkills = job.getSkills() == null ? Collections.emptyList() : job.getSkills();
        if (requiredSkills.isEmpty()) {
            return new JobScore(job, 0.0, Collections.emptyList());
        }
        double sum = 0.0;
        List<SkillGapDTO> missing = new ArrayList<>();
        for(JobSkill js : requiredSkills) {
            Skill skill = js.getSkill();
            String skillName = skill.getName().trim().toLowerCase();
            SkillLevel reqLevel = js.getLevel();
            SkillLevel have = userSkillMap.get(skillName);
            sum += recommendationUtil.addSkillDiff(skill, have, reqLevel, missing);
        }
        double skillScore = sum / requiredSkills.size();

        int jobMinExp = job.getMinExperience();

        double expFactor = userExp >= jobMinExp ? 1.0 : 0.8;
        double finalScore = SKILL_FACTOR * skillScore + EXP_FACTOR * expFactor;
        return new JobScore(job, finalScore, missing);
    }

    private List<SkillGapDTO> buildSkillGapPlan(List<JobScore> jobScores, int limit) {
        Map<String, SkillGapDTO> skillGapMap = new LinkedHashMap<>();
        for (JobScore jobScore : jobScores) {
            for (SkillGapDTO gap : jobScore.missing()) {
                String key = gap.skill().toLowerCase();
                skillGapMap.putIfAbsent(key, gap);
                if (skillGapMap.size() >= limit) break;
            }
            if (skillGapMap.size() >= limit) break;
        }
        return new ArrayList<>(skillGapMap.values());
    }

    JobRecommendationDTO toDto(JobScore jobScore) {
        return new JobRecommendationDTO(jobScore.job().getId(), jobScore.job().getTitle(),
                jobScore.job().getSalaryRangeInLPA(), recommendationUtil.getScore(jobScore.score()), recommendationUtil.getRecommendationType(jobScore.score()));
    }
}

record JobScore(Job job, double score, List<SkillGapDTO> missing) {}

