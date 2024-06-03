package com.dan.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private LocalDateTime createdDate;
    private String content;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;
    @ManyToOne(optional = true)
    @JoinColumn(name = "parent_id")
    private Comment parentComment;
}
