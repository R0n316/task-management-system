package ru.alex.taskmanagementsystem.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.alex.taskmanagementsystem.dto.TaskCreateEditDto;
import ru.alex.taskmanagementsystem.dto.TaskReadDto;
import ru.alex.taskmanagementsystem.entity.Task;
import ru.alex.taskmanagementsystem.mapper.TaskCreateEditMapper;
import ru.alex.taskmanagementsystem.mapper.TaskReadMapper;
import ru.alex.taskmanagementsystem.repository.TaskRepository;
import ru.alex.taskmanagementsystem.repository.UserRepository;

@Service
@Slf4j
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskReadMapper taskReadMapper;
    private final TaskCreateEditMapper taskCreateEditMapper;
    private final UserRepository userRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository,
                       TaskReadMapper taskReadMapper,
                       TaskCreateEditMapper taskCreateEditMapper,
                       UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.taskReadMapper = taskReadMapper;
        this.taskCreateEditMapper = taskCreateEditMapper;
        this.userRepository = userRepository;
    }

    public TaskReadDto create(TaskCreateEditDto taskDto) {
        Task task = taskCreateEditMapper.toEntity(taskDto);

        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwtToken = request.getHeader("Authorization").substring(7);
        DecodedJWT decodedJWT = JWT.decode(jwtToken);
        String email = decodedJWT.getClaim("email").asString();

        task.setAuthor(userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("author not found")));

        return taskReadMapper.toDto(taskRepository.save(task));
    }

    public Page<TaskReadDto> findAll(Pageable pageable) {
        return taskRepository.findAll(pageable)
                .map(taskReadMapper::toDto);
    }

    public TaskReadDto update(Integer taskId, TaskCreateEditDto taskDto){
        Task updatedTask = taskRepository.findById(taskId)
                .map(task -> patchUpdate(task, taskCreateEditMapper.toEntity(taskDto)))
                .orElseThrow(EntityNotFoundException::new);
        return taskReadMapper.toDto(taskRepository.save(updatedTask));
    }


    private Task patchUpdate(Task task, Task updatedTask) {
        ReflectionUtils.doWithFields(Task.class, field -> {
            field.setAccessible(true);
            Object updatedValue = ReflectionUtils.getField(field, updatedTask);
            if (updatedValue != null) {
                ReflectionUtils.setField(field, task, updatedValue);
            }
        });
        return task;
    }

}
