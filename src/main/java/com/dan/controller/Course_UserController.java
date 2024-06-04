package com.dan.controller;

import com.dan.config.Environment;
import com.dan.exception.MoMoException;
import com.dan.model.Course;
import com.dan.model.Course_User;
import com.dan.model.momo.PaymentResponse;
import com.dan.model.momo.RequestType;
import com.dan.service.Course_UserService;
import com.dan.service.JwtService;
import com.dan.service.impl.CreateOrderMoMo;
import com.dan.util.LogUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/course_user")
public class Course_UserController {
    @Autowired
    private Course_UserService course_userService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/add")
    public ResponseEntity<PaymentResponse> createCourse_User(@RequestHeader("Authorization") String token,
                                                             @RequestParam(value = "course") Course course) throws MoMoException {
        token = token.replace("Bearer ", "");
        String username = jwtService.extractUsername(token);
        return new ResponseEntity<>(course_userService.createCourse_User(course, username), HttpStatus.CREATED);
    }
}
