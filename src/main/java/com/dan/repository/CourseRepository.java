package com.dan.repository;

import com.dan.model.Category;
import com.dan.model.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    @Query("SELECT c FROM Course c WHERE CONCAT(c.name, ' ', c.object) LIKE %:keyword%" )
    Page<Course> searchByKeyword(String keyword, Pageable pageable);
    Page<Course> findByCategory(Category category, Pageable pageable);
}
