package com.learnings.tech_hub.controller;

import com.learnings.tech_hub.dto.JobDTO;
import com.learnings.tech_hub.exception.ResourceNotFoundException;
import com.learnings.tech_hub.service.JobService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobs")
public class JobController {
    private final JobService jobService;

    @PostMapping
    public ResponseEntity<JobDTO> createJob(@Valid @RequestBody JobDTO jobDTO) {
        JobDTO created = jobService.saveJob(jobDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getAllJobs() {
        return ResponseEntity.ok(jobService.getAllJobs());
    }

    @DeleteMapping("/{id}")
    public void deleteJob(@PathVariable Long id) throws ResourceNotFoundException {
        jobService.deleteJob(id);
    }
    @GetMapping("/{id}")
    public JobDTO getJobById(@PathVariable Long id) throws ResourceNotFoundException {
        return jobService.getJobById(id);
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<JobDTO> updateJob(@PathVariable Long id, @RequestBody JobDTO jobDTO) throws ResourceNotFoundException {
        JobDTO job = jobService.updateJob(jobDTO, id);
        return ResponseEntity.ok(job);
    }
}
