package com.dan.service;

import com.dan.model.Category;
import com.dan.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourseService {
    public Page<Course> getAllCourses(String keyword, Pageable pageable);
    public Course getCourseById(Long id);
    public Course createCourse(Course course);
    public void deleteCourse(Long id);
    public Course updateCourse(Long id, Course course);
    Page<Course> getCourseByCategory(Category category, Pageable pageable);
}
