package ru.alex.taskmanagementsystem.mapper;

import org.springframework.stereotype.Component;
import ru.alex.taskmanagementsystem.dto.AuthDto;
import ru.alex.taskmanagementsystem.entity.Role;
import ru.alex.taskmanagementsystem.entity.User;

@Component
public class AuthMapper implements Mapper<User, AuthDto>{
    @Override
    public User toEntity(AuthDto dto) {
        User user = new User();
        user.setEmail(dto.email());
        user.setPassword(dto.password());
        user.setName(dto.name());
        user.setRole(Role.USER);
        return user;
    }
}
