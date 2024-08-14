package ru.alex.taskmanagementsystem.dto.comment;

import jakarta.validation.constraints.NotNull;

public record CommentCreateEditDto(
        @NotNull
        Integer taskId,
        String text
) {
}
