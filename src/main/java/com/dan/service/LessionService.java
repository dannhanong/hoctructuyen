package com.dan.service;

import com.dan.model.Course;
import com.dan.model.Lession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

public interface LessionService {
    public Page<Lession> getAllLessions(String keyword, Pageable pageable);
    public Lession createLession(Course course, String name, String description, MultipartFile lessionVideo, MultipartFile lessionDocument) throws Exception;
    public Lession updateLession(Long id, Course course, String name, String description, MultipartFile lessionVideo, MultipartFile lessionDocument) throws Exception;
    public Lession getLession(Long id);
    public void deleteLession(Long id);
}
