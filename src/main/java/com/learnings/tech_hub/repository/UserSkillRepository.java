package com.learnings.tech_hub.repository;

import com.learnings.tech_hub.entity.UserSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserSkillRepository extends JpaRepository<UserSkill, Long> {
    List<UserSkill> findAllByUserId(Long userId);
    Optional<UserSkill> findByUserIdAndSkillId(Long userId, Long skillId);
    void deleteByUserIdAndSkillIdIn(Long userId, Collection<Long> skillIds);
}
