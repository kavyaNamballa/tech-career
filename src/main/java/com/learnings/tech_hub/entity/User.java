package com.learnings.tech_hub.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String name;
    @Column(length = 100, unique = true)
    private String email;
    private Integer yearsOfExperience;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSkill> skills;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_saved_jobs", joinColumns=@JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    private Set<Job> savedJobs;
}
