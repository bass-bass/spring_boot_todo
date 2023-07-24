package com.example.todo.model;

import com.example.todo.entity.Comment;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class CommentDTO {
    private Long id;
    @NotBlank
    private String comment;
    private Date createdDate;
    private Date updatedDate;
    @NotNull
    private Long todoId;

    public static CommentDTO of(Comment entity){
        CommentDTO dto = new CommentDTO();
        dto.setId(entity.getId());
        dto.setComment(entity.getComment());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        return dto;
    }
}