package com.learnings.tech_hub.controller;

import com.learnings.tech_hub.dto.JobDTO;
import com.learnings.tech_hub.exception.ResourceNotFoundException;
import com.learnings.tech_hub.service.SavedJobsService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/saved-jobs")
@Tag(
        name = "Saving jobs for user REST API's"
)
public class SavedJobsController {
    private final SavedJobsService savedJobsService;

    @PostMapping("/{jobId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<JobDTO> saveJob(@PathVariable Long userId, @PathVariable Long jobId) throws ResourceNotFoundException {
        JobDTO job = savedJobsService.saveJobForUser(userId, jobId);
        return EntityModel.of(job,
                linkTo(methodOn(this.getClass()).getSavedJobs(userId)).withRel("get-all-saved-jobs"));
    }

    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> removeSavedJob(@PathVariable Long userId, @PathVariable Long jobId) throws ResourceNotFoundException {
        savedJobsService.removeSavedJob(userId, jobId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<JobDTO>> getSavedJobs(@PathVariable Long userId) throws ResourceNotFoundException {
        return ResponseEntity.ok(savedJobsService.listSavedJobs(userId));
    }
}
