package com.example.todo.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.Date;

@Entity
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column
    private Long id;
    @Column(nullable = false)
    private String comment;

    @Column(name = "created_date",updatable = false)
    @CreatedDate
    private Date createdDate;

    @Column(name = "updated_date")
    @LastModifiedDate
    private Date updatedDate;

    @ManyToOne
    @JoinColumn(name = "todo_id")
    private Todo todo;
}