package ru.alex.taskmanagementsystem.dto.task;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record TaskCreateEditDto(
        @NotNull
        String title,
        String description,

        @Min(0)
        @Max(2)
        Integer status,

        @Min(0)
        @Max(2)
        Integer priority,

        Integer executorId
) {
}
