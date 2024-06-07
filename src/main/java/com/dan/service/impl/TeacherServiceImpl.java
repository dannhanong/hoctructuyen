package com.dan.service.impl;

import com.dan.model.Teacher;
import com.dan.model.User;
import com.dan.model.dto.CreateTeacherForm;
import com.dan.repository.TeacherRepository;
import com.dan.service.TeacherService;
import com.dan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherRepository teacherRepository;
    @Autowired
    private UserService userService;

    @Override
    public Teacher createTeacher(Teacher teacher) {
        return teacherRepository.save(teacher);
    }

    @Override
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
}
