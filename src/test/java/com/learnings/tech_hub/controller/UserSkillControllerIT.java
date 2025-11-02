package com.learnings.tech_hub.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnings.tech_hub.dto.SkillDTO;
import com.learnings.tech_hub.entity.User;
import com.learnings.tech_hub.enums.SkillLevel;
import com.learnings.tech_hub.repository.UserRepository;
import com.learnings.tech_hub.repository.UserSkillRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class UserSkillControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserSkillRepository userSkillRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Long userId;

    @BeforeEach
    void setup() {
        userSkillRepository.deleteAll();
        userRepository.deleteAll();
        User user = new User();
        user.setName("Skill User");
        user.setEmail("skilluser@example.com");
        user = userRepository.saveAndFlush(user);
        userId = user.getId();
    }

    @Test
    void getUserSkills_ReturnsEmptyList_WhenNoSkills() throws Exception {
        mockMvc.perform(get("/api/users/" + userId + "/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    void replaceSkills_ReplacesAllSkills() throws Exception {
        List<SkillDTO> initial = List.of(
                new SkillDTO("Java", SkillLevel.BEGINNER),
                new SkillDTO("SQL", SkillLevel.INTERMEDIATE)
        );
        mockMvc.perform(put("/api/users/" + userId + "/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(initial)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        List<SkillDTO> replacement = List.of(
                new SkillDTO("Python", SkillLevel.EXPERT)
        );
        String response = mockMvc.perform(put("/api/users/" + userId + "/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(replacement)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name", is("Python")))
                .andReturn().getResponse().getContentAsString();

        List<SkillDTO> result = objectMapper.readValue(response, new TypeReference<>() {});
        assertEquals(1, result.size());
        assertEquals("Python", result.getFirst().getName());

        mockMvc.perform(get("/api/users/" + userId + "/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].name", is("Python")));
    }

    @Test
    void addOrUpdateSkills_MergesSkills() throws Exception {
        List<SkillDTO> initial = List.of(
                new SkillDTO("Java", SkillLevel.BEGINNER)
        );
        mockMvc.perform(put("/api/users/" + userId + "/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(initial)))
                .andExpect(status().isOk());

        List<SkillDTO> merge = List.of(
                new SkillDTO("SQL", SkillLevel.INTERMEDIATE)
        );
        mockMvc.perform(post("/api/users/" + userId + "/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(merge)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

        mockMvc.perform(get("/api/users/" + userId + "/skills"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder("Java", "SQL")));
    }

    @Test
    void replaceSkills_ReturnsBadRequest_WhenInvalidSkill() throws Exception {
        SkillDTO invalid = new SkillDTO("", SkillLevel.BEGINNER);
        mockMvc.perform(put("/api/users/" + userId + "/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(invalid))))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserSkills_ReturnsNotFound_WhenUserDoesNotExist() throws Exception {
        mockMvc.perform(get("/api/users/99999/skills"))
                .andExpect(status().isNotFound());
    }

    @Test
    void replaceSkills_ReturnsNotFound_WhenUserDoesNotExist() throws Exception {
        SkillDTO skill = new SkillDTO("Java", SkillLevel.BEGINNER);
        mockMvc.perform(put("/api/users/99999/skills")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(List.of(skill))))
                .andExpect(status().isNotFound());
    }
}

