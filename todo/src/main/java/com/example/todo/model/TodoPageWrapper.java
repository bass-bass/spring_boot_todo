package com.example.todo.model;

import com.example.todo.entity.Todo;
import com.example.todo.model.TodoDTO;
import org.springframework.data.domain.Page;
import lombok.Getter;
import java.util.List;
import java.util.stream.Collectors;

public class TodoPageWrapper {
    @Getter
    private List<TodoDTO> dtoList;
    private Page page;
    public TodoPageWrapper(Page<Todo> page){
        List<Todo> entityList = page.getContent();
        this.dtoList = entityList.stream().map(TodoDTO::of).collect(Collectors.toList());
        this.page = page;
    }
    public int getNumber(){
        return page.getNumber();
    }

    public int getSize(){
        return page.getSize();
    }

    public int getTotalPages(){
        return page.getTotalPages();
    }

    public boolean isFirst(){
        return page.isFirst();
    }

    public boolean isLast(){
        return page.isLast();
    }

    public boolean hasPrevious(){
        return page.hasPrevious();
    }

    public boolean hasNext(){
        return page.hasNext();
    }
}
