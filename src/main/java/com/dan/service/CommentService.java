package com.dan.service;

import com.dan.model.Comment;
import com.dan.model.Course;
import com.dan.model.Lession;
import com.dan.model.dto.CommentDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CommentService {
//    Comment createCommentToCourse(CommentDto commentDto, String username);
//    Comment createCommentToComment(CommentDto commentDto, String username);
//    Comment createCommentToLession(CommentDto commentDto, String username);
    Comment createComment(CommentDto commentDto, String username);
    void deleteComment(Long id);

    Page<Comment> getCommentByCourse(Course course, Pageable pageable);
    Page<Comment> getCommentLession(Lession lession, Pageable pageable);
    Page<Comment> getCommentParentComment(Comment parentComment, Pageable pageable);

//    List<Comment> getAllComment();
//    Comment getComment(Long id);
//    Comment updateComment(Comment comment, Long id);
//    Page<Comment> getAllCommentByKeyword(String keyword, Pageable pageable);
}
