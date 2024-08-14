package ru.alex.taskmanagementsystem.mapper;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.taskmanagementsystem.dto.comment.CommentCreateEditDto;
import ru.alex.taskmanagementsystem.entity.Comment;
import ru.alex.taskmanagementsystem.repository.TaskRepository;

import java.util.Optional;

@Component
public class CommentCreateEditMapper implements Mapper<Comment, CommentCreateEditDto> {
    private final TaskRepository taskRepository;

    @Autowired
    public CommentCreateEditMapper(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    public Comment toEntity(CommentCreateEditDto dto) {
        Comment comment = new Comment();
        Optional.ofNullable(dto.taskId())
                .ifPresent(taskId -> comment.setTask(taskRepository.findById(taskId)
                        .orElseThrow(() -> new EntityNotFoundException("task not found"))));
        comment.setText(dto.text());

        return comment;
    }
}
