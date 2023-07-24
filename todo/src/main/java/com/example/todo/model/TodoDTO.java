package com.example.todo.model;

import com.example.todo.entity.Todo;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class TodoDTO {
    private Long id;
    @Size(min=2,max=100,message="2文字以上100文字以下で入力してください")
    @NotBlank
    private String title;
    @NotBlank
    private String content;

    private Date createdDate;
    private Date updatedDate;
    private List<CommentDTO> comments;

    /**
     * FactoryMethod
     * @param entity (Todo)
     * @return dto (TodoDTO)
     */
    public static TodoDTO of(Todo entity){
        TodoDTO dto = new TodoDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setUpdatedDate(entity.getUpdatedDate());
        List<CommentDTO> comments = entity.getComments().stream().map(CommentDTO::of).collect(Collectors.toList());
        dto.setComments(comments);
        return dto;
    }
}
