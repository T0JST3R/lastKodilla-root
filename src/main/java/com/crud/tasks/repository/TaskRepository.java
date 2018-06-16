package com.crud.tasks.repository;

import domain.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Long> {
    @Override
    List<Task> findAll();

    Optional<Task> findById(Long id);

    Task deleteTaskById(Long id);

    @Override
    Task save(Task task);

}
