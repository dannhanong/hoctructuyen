package com.dan.service.impl;

import com.dan.model.Course;
import com.dan.model.Report;
import com.dan.model.Teacher;
import com.dan.model.User;
import com.dan.model.dto.Course_Amount;
import com.dan.model.dto.CreateTeacherForm;
import com.dan.repository.TeacherRepository;
import com.dan.service.CourseService;
import com.dan.service.Course_UserService;
import com.dan.service.TeacherService;
import com.dan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private Course_UserService course_userService;

    @Override
    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
    @Transactional
    public Teacher createTeacher(CreateTeacherForm createTeacherForm) {
        User user = new User();
        Teacher teacher = new Teacher();
        user.setName(createTeacherForm.getName());
        user.setUsername(createTeacherForm.getUsername());
        user.setPassword(createTeacherForm.getPassword());
        user.setEmail(createTeacherForm.getEmail());
        user.setPhoneNumber(createTeacherForm.getPhoneNumber());

        teacher.setUser(userService.createUser(user));
        teacher.setCccd(createTeacherForm.getCccd());
        teacher.setSex(createTeacherForm.isSex());
        teacher.setDiscipline(createTeacherForm.getDiscipline());
        teacher.setLevel(createTeacherForm.getLevel());
        return teacherRepository.save(teacher);
    }

    @Override
    public List<Teacher> getAllTeacher() {
        return teacherRepository.findAll();
    }

    @Override
    public Teacher getTeacher(Long id) {
        return teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found teacher"));
    }

    @Override
    @Transactional
    public void deleteTeacher(Long id) {
        teacherRepository.deleteById(id);
    }

    @Override
    public Teacher updateTeacher(Teacher teacher, Long id) {
        return teacherRepository.findById(id).map(teacher1 -> {
            teacher1.setCccd(teacher.getCccd());
            teacher1.setSex(teacher.isSex());
            teacher1.setDiscipline(teacher.getDiscipline());
            teacher1.setLevel(teacher.getLevel());
            return teacherRepository.save(teacher1);
        }).orElseThrow(() -> new RuntimeException("Not found teacher"));
    }

    @Override
    @Transactional
    public Teacher updateTeacher(CreateTeacherForm createTeacherForm, Long id) {
        Teacher teacher = teacherRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found teacher"));
        User user = teacher.getUser();
        user.setName(createTeacherForm.getName());
        user.setPhoneNumber(createTeacherForm.getPhoneNumber());
        userService.updateUser(user, user.getId());

        teacher.setCccd(createTeacherForm.getCccd());
        teacher.setSex(createTeacherForm.isSex());
        teacher.setDiscipline(createTeacherForm.getDiscipline());
        teacher.setLevel(createTeacherForm.getLevel());
        teacherRepository.save(teacher);
        return teacher;
    }

    @Override
    public Teacher getTeacherByUser(User user) {
        return teacherRepository.findByUser(user);
    }

    @Override
    public Page<Teacher> getAllTeacherByKeyword(String keyword, Pageable pageable) {
        return teacherRepository.searchByKeyword(keyword, pageable);
    }

    @Override
    public Report getReport(Teacher teacher) {
        Report report = new Report();
        report.setTeacher(teacher);
        List<Course> courses = courseService.getCourseByTeacher(teacher);
        List<Course_Amount> courseAmounts = new ArrayList<>();
        for (Course course : courses) {
            int totalAmount = course_userService.totalCostOfCourse(course);
            Course_Amount course_amount = new Course_Amount();
            course_amount.setCourse(course);
            course_amount.setTotalAmount(totalAmount);
            courseAmounts.add(course_amount);
        }
        report.setCourse_amounts(courseAmounts);
        return report;
    }
}
