package com.learnings.tech_hub.controller;

import com.learnings.tech_hub.service.SkillService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SkillControllerTest {

    @Mock
    private SkillService skillService;

    @InjectMocks
    private SkillController skillController;

    @Test
    void getAllSkills_ShouldReturnSkillList() {
        List<String> mockSkills = List.of("Java", "Python");
        when(skillService.getAllSkills()).thenReturn(mockSkills);

        List<String> result = skillController.getAllSkills();
        assertEquals(mockSkills, result);
        verify(skillService, times(1)).getAllSkills();
    }

    @Test
    void addSkills_ShouldReturnAddedSkills() {
        List<String> newSkills = List.of("Go", "Singing");
        when(skillService.addSkills(newSkills)).thenReturn(newSkills);

        List<String> result = skillController.addSkills(newSkills);
        assertEquals(newSkills, result);
        verify(skillService, times(1)).addSkills(newSkills);
    }
}
