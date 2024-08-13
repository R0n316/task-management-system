package ru.alex.taskmanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.alex.taskmanagementsystem.dto.TaskFilter;
import ru.alex.taskmanagementsystem.entity.Task;

public interface TaskFilterRepository {
    Page<Task> findAllByFilter(TaskFilter filter, Pageable pageable);
}
