package com.learnings.tech_hub.controller;

import com.learnings.tech_hub.dtos.UserDTO;
import com.learnings.tech_hub.exceptions.UserAlreadyExistsException;
import com.learnings.tech_hub.exceptions.UserNotFoundException;
import com.learnings.tech_hub.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @PostMapping
    public EntityModel<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) throws UserNotFoundException, UserAlreadyExistsException {
        UserDTO user = userService.createUser(userDTO);
        return EntityModel.of(user,
                linkTo(methodOn(this.getClass()).getUser(user.getId())).withRel("user"));
    }

    @GetMapping("/{id}")
    public UserDTO getUser(@PathVariable Long id) throws UserNotFoundException {
        return userService.getUserById(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteUser(@PathVariable Long id) throws UserNotFoundException {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

}
