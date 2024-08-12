package ru.alex.taskmanagementsystem.dto;

public record CommentReadDto(
        Integer id,
        UserReadDto user,
        TaskReadDto task,
        String text
) {
}
