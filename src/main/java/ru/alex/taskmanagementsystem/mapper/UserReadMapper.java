package ru.alex.taskmanagementsystem.mapper;

import org.springframework.stereotype.Component;
import ru.alex.taskmanagementsystem.dto.user.UserReadDto;
import ru.alex.taskmanagementsystem.entity.User;

@Component
public class UserReadMapper implements Mapper<User, UserReadDto> {
    @Override
    public UserReadDto toDto(User entity) {
        return new UserReadDto(
                entity.getId(),
                entity.getName()
        );
    }
}