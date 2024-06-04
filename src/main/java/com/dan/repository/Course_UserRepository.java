package com.dan.repository;

import com.dan.model.Course_User;
import com.dan.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface Course_UserRepository extends JpaRepository<Course_User, Long>{
    Page<Course_User> findByUser(User user, Pageable pageable);
    @Query("SELECT c FROM Course_User c WHERE CONCAT(c.course.name, ' ', c.user.name) LIKE %:keyword%" )
    Page<Course_User> findByKeyword(String keyword, Pageable pageable);
}
