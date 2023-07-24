package com.example.todo.action;

import java.util.Optional;

import com.example.todo.entity.Account;
import com.example.todo.model.TodoPageWrapper;
import com.example.todo.entity.Todo;
import com.example.todo.model.TodoDTO;
import com.example.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
public class TodoService {
    enum SearchType {
        ALL(1),
        TITLE(2),
        CONTENT(3);

        private final int id;
        private SearchType(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public static SearchType get(int id){
            SearchType[] types = SearchType.values();
            for(SearchType type : types){
                if(type.getId()==id){
                    return type;
                }
            }
            return null;
        }
    }
    @Autowired
    private TodoRepository repository;

    /**
     * TodoDTOの一覧取得
     * @param pageable (Pageable)
     * @return pageWrapper (PageWrapper)
     */
    public TodoPageWrapper getList(Long accountId,Pageable pageable){
        Page<Todo> page = repository.findByAccountId(accountId,pageable);
        return new TodoPageWrapper(page);
    }
    /**
     * 新規作成と更新
     * @param dto (TodoDTO)
     */
    public void saveTodo(TodoDTO dto,Account account){
        Todo entity = new Todo();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setAccount(account);
        repository.saveAndFlush(entity);
    }
    /**
     * 指定されたTodoデータを返却
     * @param todoId (Todoのid)
     * @return dto (Optional<TodoDTO>)
     */
    public TodoDTO getTodo(Long todoId,Long accountId){
        Todo entity = repository.findById(todoId,accountId);
        TodoDTO dto = null;
        if(entity!=null){
            dto = TodoDTO.of(entity);
        }
        return dto;
    }
    /**
     * 削除
     * @param todoId (Todoのid)
     */
    public boolean deleteTodo(Long todoId,Long accountId){
        Todo todo = repository.findById(todoId,accountId);
        if(todo == null){
            return false;
        }
        repository.delete(todo);
        return true;
    }
    /**
     * 検索結果に一致するPageWrapper返却
     * searchTypeに対応するメソッド実行
     * @param keyword (formから受け取ったkeyword)
     * @param pageable (Pageable)
     * @param searchType (int)
     * @return pageWrapper (PageWrapper)
     */
    public TodoPageWrapper getSearchList(Long accountId,String keyword, Pageable pageable, int searchType){
        Page<Todo> page = null;
        SearchType type = SearchType.get(searchType);
        switch (type){
            case ALL:
                page = repository.findByTitleOrContent(accountId,keyword,pageable);
                break;
            case TITLE:
                page = repository.findByTitle(accountId,keyword,pageable);
                break;
            case CONTENT:
                page = repository.findByContent(accountId,keyword,pageable);
                break;
        }
        return new TodoPageWrapper(page);
    }
}
