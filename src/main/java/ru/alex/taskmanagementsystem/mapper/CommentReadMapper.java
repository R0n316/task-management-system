package ru.alex.taskmanagementsystem.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.alex.taskmanagementsystem.dto.comment.CommentReadDto;
import ru.alex.taskmanagementsystem.dto.user.UserReadDto;
import ru.alex.taskmanagementsystem.entity.Comment;

import java.util.Optional;

@Component
public class CommentReadMapper implements Mapper<Comment, CommentReadDto> {
    private final UserReadMapper userReadMapper;

    @Autowired
    public CommentReadMapper(UserReadMapper userReadMapper) {
        this.userReadMapper = userReadMapper;
    }

    @Override
    public CommentReadDto toDto(Comment entity) {
        UserReadDto user = Optional.ofNullable(entity.getUser())
                .map(userReadMapper::toDto)
                .orElse(null);

        return new CommentReadDto(
                entity.getId(),
                user,
                entity.getText()
        );
    }
}
