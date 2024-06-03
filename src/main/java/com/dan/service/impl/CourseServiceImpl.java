package com.dan.service.impl;

import com.dan.model.Category;
import com.dan.model.Course;
import com.dan.repository.CourseRepository;
import com.dan.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Override
    public Page<Course> getAllCourses(String keyword, Pageable pageable) {
        return courseRepository.searchByKeyword(keyword, pageable);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

    @Override
    public Course updateCourse(Long id, Course course) {
        return courseRepository.findById(id)
                .map(c -> {
                    c.setName(course.getName());
                    c.setDescription(course.getDescription());
                    c.setCost(course.getCost());
                    c.setCourseImage(course.getCourseImage());
                    c.setCourseVideo(course.getCourseVideo());
                    c.setResult(course.getResult());
                    c.setObject(course.getObject());
                    return courseRepository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public Page<Course> getCourseByCategory(Category category, Pageable pageable) {
        return courseRepository.findByCategory(category, pageable);
    }
}
