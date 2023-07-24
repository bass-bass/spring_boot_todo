package com.example.todo.repository;

import com.example.todo.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {
    @Query("SELECT a FROM Account a WHERE a.name = :name")
    Account findByName(String name);
}
