package ru.alex.taskmanagementsystem.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import ru.alex.taskmanagementsystem.dto.task.TaskFilter;
import ru.alex.taskmanagementsystem.entity.Task;
import ru.alex.taskmanagementsystem.querydsl.QPredicates;
import ru.alex.taskmanagementsystem.repository.TaskFilterRepository;

import java.util.List;

import static ru.alex.taskmanagementsystem.entity.QTask.task;

@Component
public class TaskFilterRepositoryImpl implements TaskFilterRepository {
    private final EntityManager entityManager;

    @Autowired
    public TaskFilterRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Page<Task> findAllByFilter(TaskFilter filter, PageRequest pageRequest) {
        Predicate predicate = QPredicates.builder()
                .add(filter.authorId(), task.author.id::eq)
                .add(filter.executorId(), task.executor.id::eq)
                .build();

        List<Task> result = new JPAQuery<>(entityManager)
                .select(task)
                .from(task)
                .where(predicate)
                .offset(pageRequest.getOffset())
                .limit(pageRequest.getPageSize())
                .fetch();

        Long total = new JPAQuery<>(entityManager)
                .select(task.countDistinct())
                .from(task)
                .where(predicate)
                .fetchOne();

        return new PageImpl<>(result, pageRequest, total != null ? total : 0L);
    }

}
