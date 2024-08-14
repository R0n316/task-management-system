package ru.alex.taskmanagementsystem.dto.task;

public record TaskFilter(
        Integer authorId,
        Integer executorId
) {
}
