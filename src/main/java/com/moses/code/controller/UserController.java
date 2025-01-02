package com.moses.code.controller;

import com.moses.code.entity.User;
import com.moses.code.exception.EmailAlreadyExistException;
import com.moses.code.exception.UserNotFoundException;
import com.moses.code.service.user.IUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Operation(summary = "Create a new user", description = "Register a new user in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Email already exists or invalid request")
    })
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User userRequest) throws EmailAlreadyExistException {
        try {
            User user = userService.createUser(userRequest);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (Exception e) {
            throw  new EmailAlreadyExistException("Email already exist");
        }
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all registered users.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully")
    })

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @Operation(summary = "Get user by ID", description = "Retrieve details of a specific user by their ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "User not found")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
}
