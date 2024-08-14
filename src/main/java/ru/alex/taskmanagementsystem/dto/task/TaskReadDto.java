package ru.alex.taskmanagementsystem.dto.task;

import ru.alex.taskmanagementsystem.dto.comment.CommentReadDto;
import ru.alex.taskmanagementsystem.dto.user.UserReadDto;

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
