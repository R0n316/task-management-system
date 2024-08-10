package ru.alex.taskmanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alex.taskmanagementsystem.dto.TaskCreateEditDto;
import ru.alex.taskmanagementsystem.dto.TaskReadDto;
import ru.alex.taskmanagementsystem.entity.Task;
import ru.alex.taskmanagementsystem.mapper.TaskCreateEditMapper;
import ru.alex.taskmanagementsystem.mapper.TaskReadMapper;
import ru.alex.taskmanagementsystem.repository.TaskRepository;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskReadMapper taskReadMapper;
    private final TaskCreateEditMapper taskCreateEditMapper;

    @Autowired
    public TaskService(TaskRepository taskRepository,
                       TaskReadMapper taskReadMapper,
                       TaskCreateEditMapper taskCreateEditMapper) {
        this.taskRepository = taskRepository;
        this.taskReadMapper = taskReadMapper;
        this.taskCreateEditMapper = taskCreateEditMapper;
    }

    public TaskReadDto create(TaskCreateEditDto taskDto){
        Task task = taskCreateEditMapper.toEntity(taskDto);
        return taskReadMapper.toDto(taskRepository.save(task));
    }
}
