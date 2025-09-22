package com.learnings.tech_hub.mappers;

import com.learnings.tech_hub.dtos.SkillDTO;
import com.learnings.tech_hub.dtos.UserDTO;
import com.learnings.tech_hub.entities.User;
import com.learnings.tech_hub.entities.UserSkill;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.*;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "skills", ignore = true)
    @Mapping(target = "savedJobs", ignore = true)
    User toEntity(UserDTO dto);

    @Mapping(target = "skills", source = "skills")
    UserDTO toDTO(User user);

    default List<SkillDTO> mapUserSkills(List<UserSkill> userSkills) {
        if (userSkills == null) return new ArrayList<>();
        return userSkills.stream()
                .map(this::mapUserSkillToDTO)
                .collect(Collectors.toList());
    }

    default SkillDTO mapUserSkillToDTO(UserSkill userSkill) {
        SkillDTO dto = new SkillDTO(userSkill.getSkill().getName(), userSkill.getLevel());
        return dto;
    }
}
