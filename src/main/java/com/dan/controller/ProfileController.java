package com.dan.controller;

import com.dan.model.User;
import com.dan.service.JwtService;
import com.dan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtService;

    @GetMapping("")
    public ResponseEntity<User> getCustomer(@RequestHeader("Authorization") String token) {
        token = token.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);
        User user = userService.getUserByUsername(username);
        return new  ResponseEntity(user, HttpStatus.OK);
    }

//    @PutMapping("/update")
//    public ResponseEntity<User> updateProfile(@RequestHeader("Authorization") String token, @RequestBody User user) {
//        String username = jwtService.extractUsername(token);
//        User currentUser = userService.getUserByUsername(username);
//        currentUser.setName(user.getName());
//        currentUser.setEmail(user.getEmail());
//        currentUser.setAvatar(user.getAvatar());
//        return new ResponseEntity(userService.updateUser(currentUser), HttpStatus.OK);
//    }
}
