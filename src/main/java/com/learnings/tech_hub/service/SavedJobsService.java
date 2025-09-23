package com.learnings.tech_hub.service;

import com.learnings.tech_hub.dtos.JobDTO;
import com.learnings.tech_hub.entities.Job;
import com.learnings.tech_hub.entities.User;
import com.learnings.tech_hub.exceptions.ResourceNotFoundException;
import com.learnings.tech_hub.mappers.JobMapper;
import com.learnings.tech_hub.repository.JobRepository;
import com.learnings.tech_hub.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SavedJobsService {
    private final JobRepository jobRepository;
    private final JobMapper jobMapper;
    private final UserRepository userRepository;

    @Transactional
    public JobDTO saveJobForUser(Long userId, Long jobId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found: " + jobId));

        if (user.getSavedJobs() == null) user.setSavedJobs(new HashSet<>());
        if (!user.getSavedJobs().contains(job)) {
            user.getSavedJobs().add(job);
            userRepository.save(user);
        }
        return jobMapper.toDTO(job);
    }

    @Transactional
    public void removeSavedJob(Long userId, Long jobId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found: " + jobId));
        if (user.getSavedJobs() != null && user.getSavedJobs().remove(job)) {
            userRepository.save(user);
        }
    }

    @Transactional(readOnly = true)
    public List<JobDTO> listSavedJobs(Long userId) throws ResourceNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + userId));
        if (user.getSavedJobs() == null) return Collections.emptyList();
        return user.getSavedJobs().stream()
                .map(jobMapper::toDTO)
                .collect(Collectors.toList());
    }
}
