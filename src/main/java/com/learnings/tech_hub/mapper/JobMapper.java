package com.learnings.tech_hub.mapper;

import com.learnings.tech_hub.dto.JobDTO;
import com.learnings.tech_hub.dto.SkillDTO;
import com.learnings.tech_hub.entity.Job;
import com.learnings.tech_hub.entity.JobSkill;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface JobMapper {
    @Mapping(source = "skills", target = "skills")
    JobDTO toDTO(Job job);

    @Mapping(target = "skills", ignore = true)
    Job toEntity(JobDTO jobDTO);

    List<JobDTO> toDTOs(List<Job> jobs);

    @Mapping(target = "skills", ignore = true)
    void merge(JobDTO source, @MappingTarget Job target);

    default SkillDTO mapJobSkillToDTO(JobSkill jobSkill) {
        SkillDTO skill = new SkillDTO(jobSkill.getSkill().getName(), jobSkill.getLevel());
        return skill;
    }
}
