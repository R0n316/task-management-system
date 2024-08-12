package ru.alex.taskmanagementsystem.dto;

import java.util.List;

public record TaskReadDto(
        Integer id,
        String title,
        String description,
        String status,
        String priority,
        UserReadDto author,
        UserReadDto executor,
        List<CommentReadDto> comments
) {
}
