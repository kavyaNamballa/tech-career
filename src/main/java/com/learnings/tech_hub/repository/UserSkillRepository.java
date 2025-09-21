package com.learnings.tech_hub.repository;

import com.learnings.tech_hub.entities.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
}
