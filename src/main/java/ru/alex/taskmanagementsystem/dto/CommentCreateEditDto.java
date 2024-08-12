package ru.alex.taskmanagementsystem.dto;

public record CommentCreateEditDto(
        Integer taskId,
        String text
) {
}
