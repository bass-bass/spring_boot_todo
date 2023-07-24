package com.example.todo.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;
import java.util.List;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name="todos")
public class Todo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @Column(name = "created_date",updatable = false)
    @CreatedDate
    private Date createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    private Date updatedDate;

    @OrderBy(value = "updatedDate DESC")
    @OneToMany(mappedBy = "todo",cascade = CascadeType.ALL)
    private List<Comment> comments;

    @ManyToOne
    @JoinColumn(name = "account_id")
    Account account;
}