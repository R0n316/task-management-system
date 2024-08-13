package ru.alex.taskmanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.alex.taskmanagementsystem.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task,Integer>, TaskFilterRepository {

}
