package com.dan.service;

import com.dan.exception.MoMoException;
import com.dan.model.Course;
import com.dan.model.Course_User;
import com.dan.model.Course_UserSub;
import com.dan.model.User;
import com.dan.model.momo.PaymentResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface Course_UserService {
    Page<Course_User> getAllCourseByUser(User user, Pageable pageable);
    Page<Course_User> getAllCourse_User(String keyword, Pageable pageable);
    public Course_User createCourse_User(Course_User course_user);
    public PaymentResponse createCourse_User(Course course, String username) throws MoMoException;
    public Course_User createCourse_UserBySub(Course_UserSub course_userSub);
    public Course_User updateCourse_User(Course_User course_user, Long id);
    public void deleteCourse_User(Long id);
    public int totalCostOfCourse(Course course);
}
