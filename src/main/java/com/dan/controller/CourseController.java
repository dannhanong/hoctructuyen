package com.dan.controller;

import com.dan.model.Category;
import com.dan.model.Course;
import com.dan.model.FileUpload;
import com.dan.service.CategoryService;
import com.dan.service.CourseService;
import com.dan.service.FileUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;
    @Autowired
    private FileUploadService fileUploadService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<Page<Course>> getAllCourses(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                                      @RequestParam(value = "size", defaultValue = "10") int size,
                                                      @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                                      @RequestParam(value = "order", defaultValue = "desc") String order)
    {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc(sortBy)));
        return new  ResponseEntity<>(courseService.getAllCourses(keyword, pageable), HttpStatus.OK);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<Course> createCourse(@RequestParam(value = "name") String name,
                                               @RequestParam(value = "description") String description,
                                               @RequestParam(value = "cost") int cost,
                                               @RequestParam(value = "courseImage", required = false) MultipartFile courseImage,
                                               @RequestParam(value = "courseVideo", required = false) MultipartFile courseVideo,
                                               @RequestParam(value = "result") String result,
                                               @RequestParam(value = "object") String object) throws IOException {
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
        return new ResponseEntity<>(courseService.createCourse(course), HttpStatus.CREATED);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<Course> updateCourse(@RequestParam(value = "name") String name,
                                               @RequestParam(value = "description") String description,
                                               @RequestParam(value = "cost") int cost,
                                               @RequestParam(value = "courseImage", required = false) MultipartFile courseImage,
                                               @RequestParam(value = "courseVideo", required = false) MultipartFile courseVideo,
                                               @RequestParam(value = "result") String result,
                                               @RequestParam(value = "object") String object,
                                               @PathVariable("id") Long id) throws IOException {
        Course course = courseService.getCourseById(id);
        course.setName(name);
        course.setDescription(description);
        course.setCost(cost);
        course.setResult(result);
        course.setObject(object);
        Long oldCourseImageId = null;
        Long oldCourseVideoId = null;
        if (courseImage != null && !courseImage.isEmpty()) {
            if (course.getCourseImage() != null && !course.getCourseImage().equals("")){
                oldCourseImageId = course.getCourseImage().getId();
            }
            String filecourseImageName = StringUtils.cleanPath(courseImage.getOriginalFilename());
            FileUpload fileUploadCourseImage = fileUploadService.uploadFile(filecourseImageName, courseImage);
            course.setCourseImage(fileUploadCourseImage);
        }
        if (courseVideo != null && !courseVideo.isEmpty()) {
            if (course.getCourseVideo() != null && !course.getCourseVideo().equals("")){
                oldCourseVideoId = course.getCourseVideo().getId();
            }
            String filecourseVideoName = StringUtils.cleanPath(courseVideo.getOriginalFilename());
            FileUpload fileUploadCourseVideo = fileUploadService.uploadFile(filecourseVideoName, courseVideo);
            course.setCourseVideo(fileUploadCourseVideo);
        }
        Course updatedCourse = courseService.updateCourse(id, course);
        if (oldCourseImageId != null){
            fileUploadService.deleteFile(oldCourseImageId);
        }
        return new ResponseEntity<>(updatedCourse, HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteCourse(@PathVariable("id") Long id) throws IOException {
        Course course = courseService.getCourseById(id);
        if (course.getCourseImage() != null){
            fileUploadService.deleteFile(course.getCourseImage().getId());
        }
        if (course.getCourseVideo() != null){
            fileUploadService.deleteFile(course.getCourseVideo().getId());
        }
        courseService.deleteCourse(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable("id") Long id) {
        return new ResponseEntity<>(courseService.getCourseById(id), HttpStatus.OK);
    }

    @GetMapping("/course/by-category/{id}")
    public ResponseEntity<Page<Course>> getCourseByCategory(@PathVariable("id") Long id,
                                                           @RequestParam(value = "page", defaultValue = "0") int page,
                                                           @RequestParam(value = "size", defaultValue = "10") int size,
                                                           @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                                           @RequestParam(value = "order", defaultValue = "desc") String order) {
        Category category = categoryService.getCategoryById(id);
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc(sortBy)));
        return new ResponseEntity<>(courseService.getCourseByCategory(category, pageable), HttpStatus.OK);
    }
}
