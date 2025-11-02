package com.learnings.tech_hub.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learnings.tech_hub.dto.JobDTO;
import com.learnings.tech_hub.dto.SkillDTO;
import com.learnings.tech_hub.repository.JobRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class JobControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setup() {
        jobRepository.deleteAll();
    }

    @Test
    void createJob_ReturnsCreatedJob_WhenJobDTOIsValid() throws Exception {
        JobDTO jobDTO = new JobDTO();
        jobDTO.setTitle("Backend Developer");
        jobDTO.setSalaryRangeInLPA("10-20 LPA");
        jobDTO.setMinExperience(3);
        jobDTO.setSkills(Arrays.asList(new SkillDTO("Java"), new SkillDTO("Spring Boot")));

        mockMvc.perform(post("/api/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("Backend Developer")))
                .andExpect(jsonPath("$.salaryRangeInLPA", is("10-20 LPA")))
                .andExpect(jsonPath("$.id", notNullValue()));
    }

    @Test
    void createJob_ReturnsBadRequest_WhenJobDTOIsInvalid() throws Exception {
        JobDTO jobDTO = new JobDTO();

        mockMvc.perform(post("/api/jobs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(jobDTO)))
                .andExpect(status().isBadRequest());
    }
}

