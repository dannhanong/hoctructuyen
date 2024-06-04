package com.dan.service;

import com.dan.model.Category;
import com.dan.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CourseService {
    public Page<Course> getAllCourses(String keyword, Pageable pageable);
    public Course getCourseById(Long id);
    public Course createCourse(Course course);
    public Course createCourse(String name, String description, int cost, MultipartFile courseImage,
                             MultipartFile courseVideo, String result, String object, Category category) throws IOException;
    public void deleteCourse(Long id);
    public Course updateCourse(Long id, Course course);
    public Course updateCourse(String name, String description, int cost, MultipartFile courseImage,
                               MultipartFile courseVideo, String result, String object, Category category, Long id) throws IOException;
    Page<Course> getCourseByCategory(Category category, Pageable pageable);
}
