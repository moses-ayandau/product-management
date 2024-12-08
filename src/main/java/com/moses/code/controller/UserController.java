package com.moses.code.controller;

import com.moses.code.entity.User;
import com.moses.code.exception.EmailAlreadyExistException;
import com.moses.code.exception.UserNotFoundException;
import com.moses.code.service.user.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User userRequest) throws EmailAlreadyExistException {
       try{
           User user = userService.createUser(userRequest);
           return new ResponseEntity<>(user, HttpStatus.CREATED);
       }
       catch (Exception e){
           e.printStackTrace();

       }
       return null;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) throws UserNotFoundException {
        User user = userService.getUserById(userId);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
