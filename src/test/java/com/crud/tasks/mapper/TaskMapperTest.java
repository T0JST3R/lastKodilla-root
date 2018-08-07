package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class TaskMapperTest {
    @InjectMocks
    TaskMapper taskMapper;
    Task task = new Task(3L, "test", "test");

    @Test
    public void mapToTask() {
        //Given
        TaskDto taskDto = new TaskDto();
        TaskDto taskDto1 = new TaskDto(3L, "test", "test");
        //When
        Task taskNull = taskMapper.mapToTask(taskDto);
        Task task = taskMapper.mapToTask(taskDto1);
        //Then
        assertNull(taskNull.getId());
        assertEquals(3L, task.getId(), 0);
        assertEquals("test", task.getContent());
        assertEquals("test", task.getTitle());

    }

    @Test
    public void mapToTaskDto() {
        //Given
        TaskDto taskDto = taskMapper.mapToTaskDto(task);
        //Then

        assertEquals("test", taskDto.getContent());
        assertEquals(3L, taskDto.getId(), 0);
        assertEquals("test", taskDto.getTitle());
    }

    @Test
    public void mapToTaskDtoList() {
        //Given
        List<Task> taskList = new ArrayList<>();
        taskList.add(task);
        List<TaskDto> taskDtos = taskMapper.mapToTaskDtoList(taskList);

        //Then
        assertEquals("test" ,taskDtos.get(0).getTitle());
        assertEquals(3L ,taskDtos.get(0).getId() , 0);
        assertEquals("test" , taskDtos.get(0).getContent());

    }
}