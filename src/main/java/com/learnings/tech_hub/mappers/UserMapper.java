package com.learnings.tech_hub.mappers;

import com.learnings.tech_hub.dtos.UserDTO;
import com.learnings.tech_hub.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toEntity(UserDTO dto);

    UserDTO toDTO(User user);
}
