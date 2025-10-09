package com.learnings.tech_hub.controller;

import com.learnings.tech_hub.dto.JobDTO;
import com.learnings.tech_hub.exception.ResourceNotFoundException;
import com.learnings.tech_hub.service.JobService;
import com.learnings.tech_hub.specification.JobSearchCriteria;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JobControllerTest {
    @Mock
    private JobService jobService;

    @InjectMocks
    private JobController jobController;

    @Test
    void createJob_ReturnsCreatedJob_WhenJobDTOIsValid() {
        JobDTO jobDTO = new JobDTO();
        JobDTO createdJob = new JobDTO();
        when(jobService.saveJob(jobDTO)).thenReturn(createdJob);

        ResponseEntity<JobDTO> response = jobController.createJob(jobDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(createdJob, response.getBody());
        verify(jobService, times(1)).saveJob(jobDTO);
    }

    @Test
    void getAllJobs_ReturnsJobList_WhenCriteriaAreProvided() {
        List<JobDTO> jobs = List.of(new JobDTO());
        when(jobService.getAllJobs(any(JobSearchCriteria.class))).thenReturn(jobs);

        ResponseEntity<List<JobDTO>> response = jobController.getAllJobs("Developer", 1, 5, 100000.0);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(jobs, response.getBody());
        verify(jobService, times(1)).getAllJobs(any(JobSearchCriteria.class));
    }

    @Test
    void deleteJob_ThrowsResourceNotFoundException_WhenJobDoesNotExist() throws ResourceNotFoundException {
        doThrow(new ResourceNotFoundException("Not found")).when(jobService).deleteJob(99L);

        assertThrows(ResourceNotFoundException.class, () -> jobController.deleteJob(99L));
        verify(jobService, times(1)).deleteJob(99L);
    }

    @Test
    void getJobById_ReturnsJobDTO_WhenJobExists() throws ResourceNotFoundException {
        JobDTO jobDTO = new JobDTO();
        when(jobService.getJobById(1L)).thenReturn(jobDTO);

        JobDTO result = jobController.getJobById(1L);

        assertEquals(jobDTO, result);
        verify(jobService, times(1)).getJobById(1L);
    }

    @Test
    void getJobById_ThrowsResourceNotFoundException_WhenJobDoesNotExist() throws ResourceNotFoundException {
        when(jobService.getJobById(100L)).thenThrow(new ResourceNotFoundException("Not found"));

        assertThrows(ResourceNotFoundException.class, () -> jobController.getJobById(100L));
        verify(jobService, times(1)).getJobById(100L);
    }

    @Test
    void updateJob_ReturnsUpdatedJob_WhenJobExists() throws ResourceNotFoundException {
        JobDTO jobDTO = new JobDTO();
        JobDTO updatedJob = new JobDTO();
        when(jobService.updateJob(jobDTO, 1L)).thenReturn(updatedJob);

        ResponseEntity<JobDTO> response = jobController.updateJob(1L, jobDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedJob, response.getBody());
        verify(jobService, times(1)).updateJob(jobDTO, 1L);
    }
}
