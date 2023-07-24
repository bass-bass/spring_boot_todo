package com.example.todo.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "accounts")
@Getter
@Setter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String password;
    @OneToMany(mappedBy = "account")
    private List<Todo> todoList;
}
