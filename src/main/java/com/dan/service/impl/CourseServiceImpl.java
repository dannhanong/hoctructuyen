package com.dan.service.impl;

import com.dan.model.Category;
import com.dan.model.Comment;
import com.dan.model.Course;
import com.dan.model.FileUpload;
import com.dan.model.dto.CourseDetailAndSuggest;
import com.dan.repository.CourseRepository;
import com.dan.service.CommentService;
import com.dan.service.CourseService;
import com.dan.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private CourseRepository courseRepository;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private CommentService commentService;

    @Override
    public Page<Course> getAllCourses(String keyword, Pageable pageable) {
        return courseRepository.searchByKeyword(keyword, pageable);
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public CourseDetailAndSuggest getCourseDetailAndSuggest(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        Pageable pageable = PageRequest.of(0, 4);
        List<Course> suggestCourses = courseRepository.findByCategoryOrTeacherAndIdNot(course.getCategory(), course.getTeacher(), PageRequest.of(0, 5), id);
        Pageable paginate = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("id")));
        Page<Comment> comments = commentService.getCommentByCourse(course, pageable);
        CourseDetailAndSuggest courseDetailAndSuggest = new CourseDetailAndSuggest();
        courseDetailAndSuggest.setCourse(course);
        courseDetailAndSuggest.setSuggestions(suggestCourses);
        courseDetailAndSuggest.setComments(comments);
        return courseDetailAndSuggest;
    }

    @Override
    public Course createCourse(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course createCourse(String name, String description, int cost, MultipartFile courseImage, MultipartFile courseVideo, String result, String object, Category category) throws IOException {
        Course course = new Course();
        course.setName(name);
        course.setDescription(description);
        course.setCost(cost);
        course.setResult(result);
        course.setObject(object);
        if (courseImage != null && !courseImage.isEmpty()) {
            String filecourseImageName = StringUtils.cleanPath(courseImage.getOriginalFilename());
            FileUpload fileUploadCourseImage = fileUploadService.uploadFile(filecourseImageName, courseImage);
            course.setCourseImage(fileUploadCourseImage);
        }
        if (courseVideo != null && !courseVideo.isEmpty()) {
            String filecourseVideoName = StringUtils.cleanPath(courseVideo.getOriginalFilename());
            FileUpload fileUploadCourseVideo = fileUploadService.uploadFile(filecourseVideoName, courseVideo);
            course.setCourseVideo(fileUploadCourseVideo);
        }
        course.setCategory(category);
        return courseRepository.save(course);
    }

    @Override
    public void deleteCourse(Long id) {
        Course course = courseRepository.findById(id).orElseThrow(() -> new RuntimeException("Course not found"));
        if (course.getCourseImage() != null){
            try {
                fileUploadService.deleteFile(course.getCourseImage().getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        if (course.getCourseVideo() != null){
            try {
                fileUploadService.deleteFile(course.getCourseVideo().getId());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
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
                    c.setCategory(course.getCategory());
                    return courseRepository.save(c);
                })
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public Course updateCourse(String name, String description, int cost, MultipartFile courseImage,
                               MultipartFile courseVideo, String result, String object, Category category,
                               Long id) throws IOException {
        return courseRepository.findById(id)
                .map(c -> {
                    c.setName(name);
                    c.setDescription(description);
                    c.setCost(cost);
                    c.setResult(result);
                    c.setObject(object);
                    Long oldCourseImageId = null;
                    Long oldCourseVideoId = null;
                    if (courseImage != null && !courseImage.isEmpty()) {
                        if (c.getCourseImage() != null && !c.getCourseImage().equals("")){
                            oldCourseImageId = c.getCourseImage().getId();
                        }
                        String filecourseImageName = StringUtils.cleanPath(courseImage.getOriginalFilename());
                        FileUpload fileUploadCourseImage = null;
                        try {
                            fileUploadCourseImage = fileUploadService.uploadFile(filecourseImageName, courseImage);
                            c.setCourseImage(fileUploadCourseImage);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (courseVideo != null && !courseVideo.isEmpty()) {
                        if (c.getCourseVideo() != null && !c.getCourseVideo().equals("")){
                            oldCourseVideoId = c.getCourseVideo().getId();
                        }
                        String filecourseVideoName = StringUtils.cleanPath(courseVideo.getOriginalFilename());
                        FileUpload fileUploadCourseVideo = null;
                        try {
                            fileUploadCourseVideo = fileUploadService.uploadFile(filecourseVideoName, courseVideo);
                            c.setCourseVideo(fileUploadCourseVideo);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    c.setCategory(category);
                    Course updatedCourse = courseRepository.save(c);
                    if (oldCourseImageId != null){
                        try {
                            fileUploadService.deleteFile(oldCourseImageId);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    if (oldCourseVideoId != null){
                        try {
                            fileUploadService.deleteFile(oldCourseVideoId);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    return updatedCourse;
                })
                .orElseThrow(() -> new RuntimeException("Course not found"));
    }

    @Override
    public Page<Course> getCourseByCategory(Category category, Pageable pageable) {
        return courseRepository.findByCategory(category, pageable);
    }
}
