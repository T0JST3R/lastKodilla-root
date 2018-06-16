package com.crud.tasks.repository;

import domain.Task;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task , Long> {
    List<Task> findAll();
    Task getById(Long id);
}
