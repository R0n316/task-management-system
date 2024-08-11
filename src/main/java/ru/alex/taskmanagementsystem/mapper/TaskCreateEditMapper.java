package ru.alex.taskmanagementsystem.mapper;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import ru.alex.taskmanagementsystem.dto.TaskCreateEditDto;
import ru.alex.taskmanagementsystem.entity.Priority;
import ru.alex.taskmanagementsystem.entity.Status;
import ru.alex.taskmanagementsystem.entity.Task;
import ru.alex.taskmanagementsystem.repository.UserRepository;

@Component
public class TaskCreateEditMapper implements Mapper<Task, TaskCreateEditDto> {
    private final UserRepository userRepository;

    @Autowired
    public TaskCreateEditMapper(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Task toEntity(TaskCreateEditDto dto) {
        Task task = new Task();

        HttpServletRequest request = ((ServletRequestAttributes)
                RequestContextHolder.currentRequestAttributes()).getRequest();
        String jwtToken = request.getHeader("Authorization").substring(7);
        DecodedJWT decodedJWT = JWT.decode(jwtToken);
        String email = decodedJWT.getClaim("email").asString();

        task.setAuthor(userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("author not found")));

        task.setExecutor(userRepository.findById(dto.executorId())
                .orElseThrow(() -> new EntityNotFoundException("executor not found")));

        task.setTitle(dto.title());
        task.setDescription(dto.description());
        task.setStatus(Status.values()[dto.status()]);
        task.setPriority(Priority.values()[dto.priority()]);

        return task;
    }
}