package com.learnings.tech_hub.service;

import com.learnings.tech_hub.dtos.JobDTO;
import com.learnings.tech_hub.dtos.SkillDTO;
import com.learnings.tech_hub.entities.Job;
import com.learnings.tech_hub.entities.JobSkill;
import com.learnings.tech_hub.entities.Skill;
import com.learnings.tech_hub.exceptions.ResourceNotFoundException;
import com.learnings.tech_hub.mappers.JobMapper;
import com.learnings.tech_hub.repository.JobRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class JobService {
    private final JobRepository jobRepository;
    private final SkillService skillService;
    private final JobMapper jobMapper;
    private final JobSkillService jobSkillService;

    @Transactional
    public JobDTO saveJob(JobDTO jobDTO) {
        Job job = jobMapper.toEntity(jobDTO);
        job = jobRepository.save(job);

        List<SkillDTO> skills = jobDTO.getSkills();
        if (skills != null && !skills.isEmpty()) {
            List<Skill> skillEntities = skillService.saveOrGetSkills(skills);
            List<JobSkill> jobSkills = jobSkillService.saveJobSkills(job, skills, skillEntities);
            job.setSkills(jobSkills);
            jobRepository.save(job);
        }
        return  jobMapper.toDTO(job);
    }

    @Transactional
    public JobDTO updateJob(JobDTO jobDTO, Long id) throws ResourceNotFoundException {
        Job existingJob = jobRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job not found with id: " + id));

        jobMapper.merge(jobDTO, existingJob);
        Job savedJob = jobRepository.save(existingJob);
        return jobMapper.toDTO(savedJob);
    }

    public void deleteJob(Long id) throws ResourceNotFoundException {
        if (!jobRepository.findById(id).isPresent()) {
            throw new ResourceNotFoundException("Job not found with id: " + id);
        }
        jobRepository.deleteById(id);
    }

    public JobDTO getJobById(Long id) throws ResourceNotFoundException {
        Job job = jobRepository.findById(id).orElse(null);
        if (job == null) {
            throw new ResourceNotFoundException("Job not found with id: "+id);
        }
        return jobMapper.toDTO(job);
    }

    public List<JobDTO> getAllJobs() {
        List<Job> jobs = jobRepository.findAll();
        return jobMapper.toDTOs(jobs);
    }
}
