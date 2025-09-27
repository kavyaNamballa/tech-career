package com.learnings.tech_hub.controller;

import com.learnings.tech_hub.service.SkillService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/skills")
@Tag(
        name = "Fetching and adding Skills API's"
)
public class SkillController {
    private final SkillService skillService;

    @GetMapping
    public List<String> getAllSkills() {
        return skillService.getAllSkills();
    }

    @PostMapping
    public List<String> addSkills(@RequestBody List<String> skills) {
        return skillService.addSkills(skills);
    }
}
