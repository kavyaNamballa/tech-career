package com.learnings.tech_hub.entity;

import com.learnings.tech_hub.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class JobSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Job job;

    @ManyToOne
    private Skill skill;

    @Enumerated(EnumType.STRING)
    private SkillLevel level;

    public JobSkill(Job job, Skill skill, SkillLevel level) {
        this.job = job;
        this.skill = skill;
        this.level = level;
    }
}