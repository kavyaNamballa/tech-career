package com.learnings.tech_hub.mappers;

import com.learnings.tech_hub.dtos.JobDTO;
import com.learnings.tech_hub.dtos.SkillDTO;
import com.learnings.tech_hub.entities.Job;
import com.learnings.tech_hub.entities.JobSkill;
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
