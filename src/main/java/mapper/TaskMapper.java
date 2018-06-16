package com.crud.tasks.mapper;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TaskMapper {
    public Task mapToTask(final TaskDto taskDto){
        return new Task(taskDto.getId() , taskDto.getTitle() , taskDto.getContent());
    }
    public TaskDto mapToTaskDto(final Task task){
        return new TaskDto(task.getId() , task.getTitle() , task.getContent());
    }

    public List<TaskDto> mapToTaskDtoList(final List<Task>list){
        List<TaskDto> taskDtos= new ArrayList<>();
        for (Task task: list) {
            TaskDto taskDto = new TaskDto(task.getId() , task.getTitle() , task.getContent());
            taskDtos.add(taskDto);

        }
        return taskDtos;
    }
}
