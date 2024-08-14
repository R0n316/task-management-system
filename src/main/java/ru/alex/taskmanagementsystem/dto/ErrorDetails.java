package ru.alex.taskmanagementsystem.dto;

import java.util.Map;

public record ErrorDetails(
        long timestamp,
        String message,
        Map<String,String> details,
        int status,
        String path
) {
}
