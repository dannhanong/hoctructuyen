package com.dan.service;

import com.dan.model.Teacher;
import com.dan.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TeacherService {
    Teacher createTeacher(Teacher teacher);
    List<Teacher> getAllTeacher();
    Teacher getTeacher(Long id);
    void deleteTeacher(Long id);
    Teacher updateTeacher(Teacher teacher, Long id);
    Teacher getTeacherByUser(User user);
    Page<Teacher> getAllTeacherByKeyword(String keyword, Pageable pageable);
}
