package com.crud.tasks.service;

import com.crud.tasks.domain.Task;
import com.crud.tasks.repository.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DbServiceTest {
    @InjectMocks
    DbService dbService;
    @Mock
    TaskRepository taskRepository;
    Task task = new Task(3L , "test"  , "test");


    @Test
    public void getAllTasks() {
        //Given
        List<Task> tasks1 = new ArrayList<>();
        tasks1.add(task);
        when(taskRepository.findAll()).thenReturn(tasks1);
      List<Task> tasks = dbService.getAllTasks();
        assertEquals(3L , tasks.get(0).getId() , 0);

    }

    @Test
    public void getTask() {
        //Given
        when(taskRepository.findById(2L)).thenReturn(Optional.of(task));
        Optional<Task> task = dbService.getTask(2L);

        assertTrue(task.isPresent());

    }

    @Test
    public void deleteTaskById() {
        when(taskRepository.deleteTaskById(3L)).thenReturn(task);
        Task task = dbService.deleteTaskById(3L);

        assertEquals(3L , task.getId() , 0);
    }

    @Test
    public void saveTask() {
        when(taskRepository.save(task)).thenReturn(task);
        Task taskk = dbService.saveTask(task);

        assertEquals(3L , taskk.getId(), 0);
    }
}