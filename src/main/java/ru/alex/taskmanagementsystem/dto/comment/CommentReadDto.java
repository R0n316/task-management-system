package ru.alex.taskmanagementsystem.dto.comment;

import ru.alex.taskmanagementsystem.dto.user.UserReadDto;

public record CommentReadDto(
        Integer id,
        UserReadDto user,
        String text
) {
}
