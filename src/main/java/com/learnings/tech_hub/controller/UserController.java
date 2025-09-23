package com.learnings.tech_hub.controller;

import com.learnings.tech_hub.dtos.UserDTO;
import com.learnings.tech_hub.exceptions.UserAlreadyExistsException;
import com.learnings.tech_hub.exceptions.ResourceNotFoundException;
import com.learnings.tech_hub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EntityModel<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) throws ResourceNotFoundException, UserAlreadyExistsException {
        UserDTO user = userService.createUser(userDTO);
        return EntityModel.of(user,
                linkTo(methodOn(this.getClass()).getUser(user.getId())).withRel("user"));
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

}
