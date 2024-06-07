package com.dan.repository;

import com.dan.model.Comment;
import com.dan.model.Course;
import com.dan.model.Lession;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByCourse(Course course, Pageable pageable);
    Page<Comment> findByLession(Lession lession, Pageable pageable);
    Page<Comment> findByParentComment(Comment parentComment, Pageable pageable);
}
