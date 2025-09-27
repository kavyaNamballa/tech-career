package com.learnings.tech_hub.controller;

import com.learnings.tech_hub.dto.SkillDTO;
import com.learnings.tech_hub.enums.UpsertMode;
import com.learnings.tech_hub.exception.ResourceNotFoundException;
import com.learnings.tech_hub.service.UserSkillService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/skills")
@RequiredArgsConstructor
@Tag(
        name = "Adding or Updating User Skills API's"
)
public class UserSkillController {

    private final UserSkillService userSkillService;

    @GetMapping
    public List<SkillDTO> getUserSkills(@PathVariable Long userId) throws ResourceNotFoundException {
        return userSkillService.getUserSkills(userId);
    }

    @PutMapping
    public ResponseEntity<List<SkillDTO>> replaceSkills(@PathVariable Long userId,
                                                        @RequestBody @Valid List<@Valid SkillDTO> skills) throws ResourceNotFoundException {
        List<SkillDTO> updated = userSkillService.upsertUserSkills(userId, skills, UpsertMode.REPLACE);
        return ResponseEntity.ok(updated);
    }

    @PostMapping
    public ResponseEntity<List<SkillDTO>> addOrUpdateSkills(@PathVariable Long userId,
                                                            @RequestBody @Valid List<@Valid SkillDTO> skills) throws ResourceNotFoundException {
        List<SkillDTO> updated = userSkillService.upsertUserSkills(userId, skills, UpsertMode.MERGE);
        return ResponseEntity.ok(updated);
    }
}
