package com.learnings.tech_hub.entities;

import com.learnings.tech_hub.enums.SkillLevel;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserSkill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Skill skill;

    @Enumerated(EnumType.STRING)
    private SkillLevel level;
}