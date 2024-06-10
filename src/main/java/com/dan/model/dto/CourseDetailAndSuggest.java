package com.dan.model.dto;

import com.dan.model.Comment;
import com.dan.model.Course;
import com.dan.model.Lession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseDetailAndSuggest {
    private Course course;
    private List<Course> suggestions;
    private Page<Comment> comments;
    private List<Lession> lessions;
}
