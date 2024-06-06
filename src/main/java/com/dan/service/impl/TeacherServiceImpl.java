package com.dan.service.impl;

import com.dan.model.Teacher;
import com.dan.model.User;
import com.dan.service.TeacherService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Override
    public Teacher createTeacher(Teacher teacher) {
        return null;
    }

    @Override
    public List<Teacher> getAllTeacher() {
        return null;
    }

    @Override
    public Teacher getTeacher(Long id) {
        return null;
    }

    @Override
    public void deleteTeacher(Long id) {

    }

    @Override
    public Teacher updateTeacher(Teacher teacher, Long id) {
        return null;
    }

    @Override
    public Teacher getTeacherByUser(User user) {
        return null;
    }

    @Override
    public Page<Teacher> getAllTeacherByKeyword(String keyword, Pageable pageable) {
        return null;
    }
}
