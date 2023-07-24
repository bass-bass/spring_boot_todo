package com.example.todo.action;

import com.example.todo.entity.Comment;
import com.example.todo.entity.Todo;
import com.example.todo.model.CommentDTO;
import com.example.todo.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private CommentRepository repository;

    /**
     * コメントの登録・更新
     * DTOからEntityに変換して保存
     * @param dto (CommentDTO)
     */
    public void saveComment(CommentDTO dto){
        Comment entity = new Comment();
        entity.setId(dto.getId());
        entity.setComment(dto.getComment());
        Todo todo = new Todo();
        todo.setId(dto.getTodoId());
        entity.setTodo(todo);
        repository.saveAndFlush(entity);
    }

    /**
     * コメントの削除
     * @param commentId
     */
    public void deleteComment(Long commentId){
        Comment comment = repository.findCommentById(commentId);
        repository.delete(comment);
    }

}