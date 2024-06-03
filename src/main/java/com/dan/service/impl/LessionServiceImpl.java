package com.dan.service.impl;

import com.dan.model.Course;
import com.dan.model.FileUpload;
import com.dan.model.Lession;
import com.dan.repository.LessionRepository;
import com.dan.service.FileUploadService;
import com.dan.service.LessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class LessionServiceImpl implements LessionService {
    @Autowired
    private LessionRepository lessionRepository;
    @Autowired
    private FileUploadService fileUploadService;

    @Override
    public Page<Lession> getAllLessions(String keyword, Pageable pageable) {
        return lessionRepository.searchByKeyword(keyword, pageable);
    }

    @Override
    public Lession createLession(Course course, String name, String description, MultipartFile lessionVideo, MultipartFile lessionDocument) throws IOException {
        Lession lession = new Lession();
        lession.setName(name);
        lession.setDescription(description);
        lession.setCourse(course);
        if (lessionVideo != null && !lessionVideo.isEmpty()) {
            String fileLessionVideoName = StringUtils.cleanPath(lessionVideo.getOriginalFilename());
            FileUpload fileUploadLessionVideo = fileUploadService.uploadFile(fileLessionVideoName, lessionVideo);
            lession.setLessionVideo(fileUploadLessionVideo);
        }
        if (lessionDocument != null && !lessionDocument.isEmpty()) {
            String fileLessionDocumentName = StringUtils.cleanPath(lessionDocument.getOriginalFilename());
            FileUpload fileUploadLessionDocument = fileUploadService.uploadFile(fileLessionDocumentName, lessionDocument);
            lession.setLessionDocument(fileUploadLessionDocument);
        }
        return lessionRepository.save(lession);
    }

    @Override
    public Lession updateLession(Long id, Course course, String name, String description, MultipartFile lessionVideo, MultipartFile lessionDocument) throws IOException {
        return lessionRepository.findById(id).map(l -> {
            l.setCourse(course);
            l.setName(name);
            l.setDescription(description);
            Long oldLessionVideoId = null;
            Long oldLessionDocumentId = null;
            if (lessionVideo != null && !lessionVideo.isEmpty()) {
                if (l.getLessionVideo() != null && !l.getLessionVideo().equals("")) {
                    oldLessionVideoId = l.getLessionVideo().getId();
                }
                try {
                    String fileLessionVideoName = StringUtils.cleanPath(lessionVideo.getOriginalFilename());
                    FileUpload fileUploadLessionVideo = fileUploadLessionVideo = fileUploadService.uploadFile(fileLessionVideoName, lessionVideo);
                    l.setLessionVideo(fileUploadLessionVideo);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (lessionDocument != null && !lessionDocument.isEmpty()) {
                if (l.getLessionDocument() != null && !l.getLessionDocument().equals("")) {
                    oldLessionDocumentId = l.getLessionDocument().getId();
                }
                try {
                    String fileLessionDocumentName = StringUtils.cleanPath(lessionDocument.getOriginalFilename());
                    FileUpload fileUploadLessionDocument = fileUploadService.uploadFile(fileLessionDocumentName, lessionDocument);
                    l.setLessionDocument(fileUploadLessionDocument);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            return lessionRepository.save(l);
        }).orElseThrow(() -> new RuntimeException("Not found lession"));
    }

    @Override
    public Lession getLession(Long id) {
        return lessionRepository.findById(id).orElseThrow(() -> new RuntimeException("Not found lession"));
    }

    @Override
    public void deleteLession(Long id) {
        lessionRepository.deleteById(id);
    }
}
