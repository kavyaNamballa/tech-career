package com.learnings.tech_hub.repository;

import com.learnings.tech_hub.entities.JobSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JobSkillRepository extends JpaRepository<JobSkill, Long> {
}
