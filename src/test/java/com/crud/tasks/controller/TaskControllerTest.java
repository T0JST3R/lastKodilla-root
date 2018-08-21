package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.domain.TaskDto;
import com.crud.tasks.mapper.TaskMapper;
import com.crud.tasks.repository.TaskRepository;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    private Task task = new Task(3L, "Test", "Test");
    private TaskDto taskDto = new TaskDto(3L, "TestDto", "testt");
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService dbService;
    @MockBean
    private TaskMapper taskMapper;
    @MockBean
    private TaskRepository taskRepository;


    @Test
    public void shouldSaveTask() throws Exception {

        when(taskMapper.mapToTask(ArgumentMatchers.any())).thenReturn(task);
        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);
        when(dbService.saveTask(ArgumentMatchers.any())).thenReturn(task);
        when(taskMapper.mapToTaskDto(task)).thenReturn(taskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(taskDto);
        //When & Then
        mockMvc.perform(post("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.title", is("TestDto")))
                .andExpect(jsonPath("$.content", is("testt")));

    }

    @Test
    public void shouldUpdateTask() throws Exception {
        when(taskMapper.mapToTask(ArgumentMatchers.any(TaskDto.class))).thenReturn(task);
        when(dbService.saveTask(any())).thenReturn(task);
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any())).thenReturn(taskDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        //When & Then
        mockMvc.perform(put("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.title", is("TestDto")))
                .andExpect(jsonPath("$.content", is("testt")));


    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //When & Then
        mockMvc.perform(delete("/v1/tasks/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetTasksEndpoint() throws Exception {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);
        when(taskMapper.mapToTaskDtoList(Collections.singletonList(task))).thenReturn(Collections.singletonList(taskDto));

        when(dbService.getAllTasks()).thenReturn(tasks);
        //When & Then
        mockMvc.perform(get("/v1/tasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id", is(3)))
                .andExpect(jsonPath("$[0].title", is("TestDto")))
                .andExpect(jsonPath("$[0].content", is("testt")));
    }

    @Test
    public void testGetTaskEndpoint() throws Exception {
        when(dbService.getTask(ArgumentMatchers.any())).thenReturn(Optional.of(task));
        when(taskRepository.findById(anyLong())).thenReturn(Optional.of(task));
        when(taskMapper.mapToTaskDto(ArgumentMatchers.any())).thenReturn(taskDto);
        //When & Then
        mockMvc.perform(get("/v1/task/1"))
                //   .contentType(MediaType.APPLICATION_JSON))
//                .andExpect(content().string("error"))
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.title", is("TestDto")))
                .andExpect(jsonPath("$.content", is("testt")));
    }
}