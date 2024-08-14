package ru.alex.taskmanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import ru.alex.taskmanagementsystem.dto.task.TaskFilter;
import ru.alex.taskmanagementsystem.entity.Task;

public interface TaskFilterRepository {
    Page<Task> findAllByFilter(TaskFilter filter, PageRequest pageRequest);
}
