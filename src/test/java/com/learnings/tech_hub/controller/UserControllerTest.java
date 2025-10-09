package com.learnings.tech_hub.controller;

import com.learnings.tech_hub.dto.RecommendationResponse;
import com.learnings.tech_hub.dto.UserDTO;
import com.learnings.tech_hub.exception.ResourceNotFoundException;
import com.learnings.tech_hub.exception.UserAlreadyExistsException;
import com.learnings.tech_hub.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    // normal flow - positive test cases

    @Test
    void createUser_ShouldReturnCreatedUser() throws ResourceNotFoundException, UserAlreadyExistsException {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(1L);
        when(userService.createUser(any(UserDTO.class))).thenReturn(userDTO);
        EntityModel<UserDTO> res = userController.createUser(userDTO);
        assertNotNull(res);
        assertEquals(1L, res.getContent().getId());
        verify(userService, times(1)).createUser(any(UserDTO.class));
    }

    @Test
    void getUsers_ShouldReturnUserList() {
        UserDTO user1 = new UserDTO();
        UserDTO user2 = new UserDTO();
        when(userService.getAllUsers()).thenReturn(Arrays.asList(user1, user2));

        List<UserDTO> result = userController.getUsers();

        assertEquals(2, result.size());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUsers_ShouldReturnEmptyList_WhenNoUsersExist() {
        when(userService.getAllUsers()).thenReturn(List.of());

        List<UserDTO> result = userController.getUsers();

        assertTrue(result.isEmpty());
        verify(userService, times(1)).getAllUsers();
    }

    @Test
    void getUser_ShouldReturnUser() throws ResourceNotFoundException {
        UserDTO user = new UserDTO();
        user.setId(1L);
        when(userService.getUserById(1L)).thenReturn(user);

        UserDTO result = userController.getUser(1L);

        assertEquals(1L, result.getId());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void deleteUser_ShouldReturnNoContent() throws ResourceNotFoundException {
        doNothing().when(userService).deleteUserById(1L);

        ResponseEntity<Object> response = userController.deleteUser(1L);

        assertEquals(204, response.getStatusCode().value());
        verify(userService, times(1)).deleteUserById(1L);
    }

    @Test
    void getRecommendation_ShouldReturnRecommendationResponse() throws ResourceNotFoundException {
        RecommendationResponse response = new RecommendationResponse(null,null,null);
        when(userService.getRecommendations(1L, 5)).thenReturn(response);

        RecommendationResponse result = userController.getRecommendation(1L, 5);

        assertNotNull(result);
        verify(userService, times(1)).getRecommendations(1L, 5);
    }

    //exception flow - negative test cases

    @Test
    void createUser_ShouldThrowUserAlreadyExistsException() throws UserAlreadyExistsException {
        UserDTO userDTO = new UserDTO();
        when(userService.createUser(any(UserDTO.class))).thenThrow(UserAlreadyExistsException.class);
        assertThrows(UserAlreadyExistsException.class, () -> userController.createUser(userDTO));
        verify(userService, times(1)).createUser(any(UserDTO.class));
    }

    @Test
    void getUser_ShouldThrowResourceNotFoundException() throws ResourceNotFoundException {
        when(userService.getUserById(100L)).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> userController.getUser(100L));
        verify(userService, times(1)).getUserById(100L);
    }

    @Test
    void deleteUser_ShouldThrowResourceNotFoundException() throws ResourceNotFoundException {
        // this is for methods with void return type
        doThrow(ResourceNotFoundException.class).when(userService).deleteUserById(100L);
        assertThrows(ResourceNotFoundException.class, () -> userController.deleteUser(100L));
        verify(userService, times(1)).deleteUserById(100L);
    }

    @Test
    void getRecommendation_ShouldThrowResourceNotFoundException_WhenUserDoesNotExist() throws ResourceNotFoundException {
        when(userService.getRecommendations(999L, 5)).thenThrow(ResourceNotFoundException.class);

        assertThrows(ResourceNotFoundException.class, () -> userController.getRecommendation(999L, 5));
        verify(userService, times(1)).getRecommendations(999L, 5);
    }
}
