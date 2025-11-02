package com.learnings.tech_hub.controller;

import com.learnings.tech_hub.dto.SkillDTO;
import com.learnings.tech_hub.enums.UpsertMode;
import com.learnings.tech_hub.exception.ResourceNotFoundException;
import com.learnings.tech_hub.service.UserSkillService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserSkillControllerTest {

    @Mock
    private UserSkillService userSkillService;

    @InjectMocks
    private UserSkillController userSkillController;

    private SkillDTO skill1;
    private SkillDTO skill2;

    @BeforeEach
    void setUp() {
        skill1 = new SkillDTO();
        skill2 = new SkillDTO();
    }

    // positive test cases
    @Test
    void getUserSkills_ShouldReturnSkillList() throws ResourceNotFoundException {
        when(userSkillService.getUserSkills(1L)).thenReturn(Arrays.asList(skill1, skill2));

        List<SkillDTO> result = userSkillController.getUserSkills(1L);

        assertEquals(2, result.size());
        verify(userSkillService, times(1)).getUserSkills(1L);
    }

    @Test
    void replaceSkills_ShouldReturnUpdatedSkills() throws ResourceNotFoundException {
        List<SkillDTO> skills = Arrays.asList(skill1, skill2);
        when(userSkillService.upsertUserSkills(1L, skills, UpsertMode.REPLACE)).thenReturn(skills);

        ResponseEntity<List<SkillDTO>> response = userSkillController.replaceSkills(1L, skills);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(skills, response.getBody());
        verify(userSkillService, times(1)).upsertUserSkills(1L, skills, UpsertMode.REPLACE);
    }

    @Test
    void addOrUpdateSkills_ShouldReturnUpdatedSkills() throws ResourceNotFoundException {
        List<SkillDTO> skills = Arrays.asList(skill1, skill2);
        when(userSkillService.upsertUserSkills(1L, skills, UpsertMode.MERGE)).thenReturn(skills);

        ResponseEntity<List<SkillDTO>> response = userSkillController.addOrUpdateSkills(1L, skills);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(skills, response.getBody());
        verify(userSkillService, times(1)).upsertUserSkills(1L, skills, UpsertMode.MERGE);
    }
}

