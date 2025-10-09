package com.learnings.tech_hub.controller;

import com.learnings.tech_hub.dto.JobDTO;
import com.learnings.tech_hub.exception.ResourceNotFoundException;
import com.learnings.tech_hub.service.SavedJobsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SavedJobsControllerTest {

    @Mock
    private SavedJobsService savedJobsService;

    @InjectMocks
    private SavedJobsController savedJobsController;

    @Test
    void saveJob_ReturnsEntityModel_WhenJobIsSaved() throws ResourceNotFoundException {
        JobDTO jobDTO = new JobDTO();
        when(savedJobsService.saveJobForUser(1L, 2L)).thenReturn(jobDTO);

        EntityModel<JobDTO> result = savedJobsController.saveJob(1L, 2L);

        assertEquals(jobDTO, result.getContent());
        verify(savedJobsService, times(1)).saveJobForUser(1L, 2L);
    }

    @Test
    void removeSavedJob_ReturnsNoContent_WhenJobIsRemoved() throws ResourceNotFoundException {
        doNothing().when(savedJobsService).removeSavedJob(1L, 2L);

        ResponseEntity<Void> response = savedJobsController.removeSavedJob(1L, 2L);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(savedJobsService, times(1)).removeSavedJob(1L, 2L);
    }

    @Test
    void getSavedJobs_ReturnsListOfJobs_WhenJobsExist() throws ResourceNotFoundException {
        List<JobDTO> jobs = List.of(new JobDTO());
        when(savedJobsService.listSavedJobs(1L)).thenReturn(jobs);

        ResponseEntity<List<JobDTO>> response = savedJobsController.getSavedJobs(1L);

        assertEquals(jobs, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(savedJobsService, times(1)).listSavedJobs(1L);
    }

    @Test
    void getSavedJobs_ReturnsEmptyList_WhenNoJobsExist() throws ResourceNotFoundException {
        when(savedJobsService.listSavedJobs(1L)).thenReturn(List.of());

        ResponseEntity<List<JobDTO>> response = savedJobsController.getSavedJobs(1L);

        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(savedJobsService, times(1)).listSavedJobs(1L);
    }
}

