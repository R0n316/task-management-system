package ru.alex.taskmanagementsystem.dto;

import jakarta.validation.constraints.Min;

public record PaginationRequest(
        @Min(1)
        int pageSize,

        @Min(0)
        int pageNumber
) {
}
