package ru.alex.taskmanagementsystem.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = "comments")
@EqualsAndHashCode(exclude = "comments")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String title;
    private String description;

    @Enumerated
    private Status status;

    @Enumerated
    private Priority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author", referencedColumnName = "id")
    private User author;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "executor", referencedColumnName = "id")
    private User executor;

    @OneToMany(mappedBy = "task")
    private List<Comment> comments;
}
