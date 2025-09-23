package com.learnings.tech_hub.mapper;

import com.learnings.tech_hub.dto.SkillDTO;
import com.learnings.tech_hub.entity.Skill;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SkillMapper {
    Skill toEntity(SkillDTO skillDTO);
    SkillDTO toDto(Skill skill);
    List<SkillDTO> toDtos(List<Skill> skills);
    List<Skill> toEntities(List<SkillDTO> skillDTOs);
}
