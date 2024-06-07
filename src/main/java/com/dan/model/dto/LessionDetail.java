package com.dan.model.dto;

import com.dan.model.Comment;
import com.dan.model.Lession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessionDetail {
    Lession lession;
    Page<Comment> comments;
}
