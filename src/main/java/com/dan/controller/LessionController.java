package com.dan.controller;

import com.dan.model.Course;
import com.dan.model.Lession;
import com.dan.model.dto.ResponseMessage;
import com.dan.service.LessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/lessions")
public class LessionController {
    @Autowired
    private LessionService lessionService;

    @GetMapping("")
    public ResponseEntity<Page<Lession>> getAllLessions(@RequestParam(value = "keyword", defaultValue = "") String keyword,
                                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                                        @RequestParam(value = "size", defaultValue = "10") int size,
                                                        @RequestParam(value = "sortBy", defaultValue = "id") String sortBy,
                                                        @RequestParam(value = "order", defaultValue = "desc") String order) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Order.desc(sortBy)));
        return new ResponseEntity(lessionService.getAllLessions(keyword, pageable), HttpStatus.OK);
    }

    @PostMapping("/admin/add")
    public ResponseEntity<Lession> createLession(@RequestParam(value = "course") Course course,
                                                 @RequestParam(value = "name") String name,
                                                 @RequestParam(value = "description") String description,
                                                 @RequestParam(value = "lessionVideo") MultipartFile lessionVideo,
                                                 @RequestParam(value = "lessionDocument") MultipartFile lessionDocument) throws Exception {
        return new ResponseEntity(lessionService.createLession(course, name, description, lessionVideo, lessionDocument), HttpStatus.CREATED);
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<Lession> updateLession(@PathVariable(value = "id") Long id,
                                                 @RequestParam(value = "course") Course course,
                                                 @RequestParam(value = "name") String name,
                                                 @RequestParam(value = "description") String description,
                                                 @RequestParam(value = "lessionVideo") MultipartFile lessionVideo,
                                                 @RequestParam(value = "lessionDocument") MultipartFile lessionDocument) throws Exception{
        return new ResponseEntity(lessionService.updateLession(id, course, name, description, lessionVideo, lessionDocument), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Lession> getLession(@PathVariable(value = "id") Long id) {
        return new ResponseEntity(lessionService.getLession(id), HttpStatus.OK);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<ResponseMessage> deleteLession(@PathVariable(value = "id") Long id) {
        lessionService.deleteLession(id);
        return new ResponseEntity(new ResponseMessage("Lession deleted successfully"), HttpStatus.OK);
    }
}
