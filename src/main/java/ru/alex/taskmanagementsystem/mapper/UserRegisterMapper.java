package ru.alex.taskmanagementsystem.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import ru.alex.taskmanagementsystem.dto.user.UserRegisterDto;
import ru.alex.taskmanagementsystem.entity.Role;
import ru.alex.taskmanagementsystem.entity.User;

import java.util.Optional;

@Component
public class UserRegisterMapper implements Mapper<User, UserRegisterDto> {
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserRegisterMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User toEntity(UserRegisterDto dto) {
        User user = new User();

        Optional.ofNullable(dto.password())
                .filter(StringUtils::hasText)
                .map(passwordEncoder::encode)
                .ifPresent(user::setPassword);

        user.setEmail(dto.email());
        user.setName(dto.name());
        user.setRole(Role.USER);
        return user;
    }
}
