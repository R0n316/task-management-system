package ru.alex.taskmanagementsystem.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UserLoginDto(
        @Email
        String email,

        @Size(min = 3, max = 256)
        String password
) {
}
