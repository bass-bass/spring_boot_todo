package com.example.todo.repository;

import com.example.todo.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>{
    @Query("SELECT t FROM Todo t WHERE t.account.id = :accountId AND (t.title LIKE %:keyword% OR t.content LIKE %:keyword%) ORDER BY t.updatedDate DESC")
    Page<Todo> findByTitleOrContent(@Param("accountId") Long accountId,@Param("keyword") String keyword,Pageable pageable);
    @Query("SELECT t FROM Todo t WHERE t.account.id = :accountId AND t.title LIKE %:keyword% ORDER BY t.updatedDate DESC")
    Page<Todo> findByTitle(@Param("accountId") Long accountId,@Param("keyword")String keyword,Pageable pageable);
    @Query("SELECT t FROM Todo t WHERE t.account.id = :accountId AND t.content LIKE %:keyword% ORDER BY t.updatedDate DESC")
    Page<Todo> findByContent(@Param("accountId") Long accountId,@Param("keyword") String keyword,Pageable pageable);
    @Query("SELECT t FROM Todo t WHERE t.account.id = :accountId ORDER BY t.updatedDate DESC")
    Page<Todo> findByAccountId(@Param("accountId") Long accountId,Pageable pageable);
    @Query("SELECT t FROM Todo t WHERE t.id = :todoId AND t.account.id = :accountId")
    Todo findById(@Param("todoId") Long todoId,@Param("accountId") Long accountId);
}