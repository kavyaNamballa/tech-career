package com.learnings.tech_hub.service;

import com.learnings.tech_hub.dto.JobDTO;
import com.learnings.tech_hub.dto.SkillDTO;
import com.learnings.tech_hub.entity.Job;
import com.learnings.tech_hub.entity.JobSkill;
import com.learnings.tech_hub.entity.Skill;
import com.learnings.tech_hub.exception.ResourceNotFoundException;
import com.learnings.tech_hub.mapper.JobMapper;
import com.learnings.tech_hub.repository.JobRepository;
import com.learnings.tech_hub.specification.JobSearchCriteria;
import com.learnings.tech_hub.specification.JobSpecification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Slf4j
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

    public List<JobDTO> getAllJobs(JobSearchCriteria  searchCriteria) {
        Specification<Job> spec = JobSpecification.buildSpec(searchCriteria);
        List<Job> jobs = jobRepository.findAll(spec);
        var salaryCriteria = searchCriteria.getSalaryInLPA();
        if (salaryCriteria != null) {
            jobs = jobs.stream()
                    .filter(job -> salaryInBetween(job.getSalaryRangeInLPA(), salaryCriteria))
                    .toList();
        }
        return jobMapper.toDTOs(jobs);
    }

    private boolean salaryInBetween(String salaryRange, Double salary) {
        if (salaryRange == null) return false;
        String rawSalaryRange = salaryRange.replaceAll("\\s*LPA\\s*$","");
        String[] split = rawSalaryRange.split("-");
        if (split.length != 2) return false;
        try {
            Double low = Double.parseDouble(split[0]);
            Double upper = Double.parseDouble(split[1]);
            if (salary < low || salary > upper) return false;
        } catch (NumberFormatException e) {
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}
