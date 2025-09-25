package com.learnings.tech_hub.specification;

import com.learnings.tech_hub.entity.Job;
import org.springframework.data.jpa.domain.Specification;

public class JobSpecification {

    private static Specification<Job> titleContains(String title) {
        return (root, query, cb) -> {
            if (title == null || title.isBlank()) return cb.conjunction();
            return cb.like(cb.lower(root.get("title")), "%" + title.trim().toLowerCase() + "%");
        };
    }

    private static Specification<Job> minExperienceLessThanOrEqual(Integer val) {
        return (root, query, cb) -> {
            if (val==null || val < 0) return cb.conjunction();
            return cb.le(root.get("minExperience"), val);
        };
    }

    private static Specification<Job> minExperienceGreaterThanOrEqual(Integer val) {
        return (root, query, cb) -> {
            if (val==null || val < 0) return cb.conjunction();
            return cb.ge(root.get("minExperience"), val);
        };
    }

    public static Specification<Job> buildSpec(JobSearchCriteria searchCriteria) {
        Specification<Job> spec = Specification.unrestricted();
        if (searchCriteria.getTitleContains() != null) {
            spec = spec.and(titleContains(searchCriteria.getTitleContains()));
        }
        if (searchCriteria.getMinExperienceLessThanOrEqual() != null) {
            spec = spec.and(minExperienceLessThanOrEqual(searchCriteria.getMinExperienceLessThanOrEqual()));
        }
        if (searchCriteria.getMinExperienceGreaterThanOrEqual() != null) {
            spec = spec.and(minExperienceGreaterThanOrEqual(searchCriteria.getMinExperienceGreaterThanOrEqual()));
        }
        return spec;
    }
}
