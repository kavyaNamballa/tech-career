package com.learnings.tech_hub.controller;

import com.learnings.tech_hub.dto.RecommendationResponse;
import com.learnings.tech_hub.dto.UserDTO;
import com.learnings.tech_hub.exception.UserAlreadyExistsException;
import com.learnings.tech_hub.exception.ResourceNotFoundException;
import com.learnings.tech_hub.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Tag(
        name = "CRUD REST API's for user details"
)
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) throws ResourceNotFoundException, UserAlreadyExistsException {
        UserDTO user = userService.createUser(userDTO);
        return EntityModel.of(user,
                linkTo(methodOn(this.getClass()).getUser(user.getId())).withRel("user"));
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) throws ResourceNotFoundException {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) throws ResourceNotFoundException {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/recommendation/{topN}")
    public RecommendationResponse  getRecommendation(@PathVariable Long userId, @PathVariable int topN) throws ResourceNotFoundException {
        return userService.getRecommendations(userId, topN);
    }
}
