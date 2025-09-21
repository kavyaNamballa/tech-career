package com.learnings.tech_hub.mappers;

import com.learnings.tech_hub.dtos.SkillDTO;
import com.learnings.tech_hub.entities.Skill;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    Skill toEntity(SkillDTO skillDTO);
    SkillDTO toDto(Skill skill);
    List<SkillDTO> toDtos(List<Skill> skills);
    List<Skill> toEntities(List<SkillDTO> skillDTOs);
}
