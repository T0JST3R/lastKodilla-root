package com.crud.tasks.controller;

import com.crud.tasks.domain.Task;
import com.crud.tasks.service.DbService;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TaskController.class)
public class TaskControllerTest {
    private Task task = new Task(3L, "Test", "Test");
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private DbService dbService;


    @Test
    public void shouldSaveTask() throws Exception {
        when(dbService.saveTask(any())).thenReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        //When & Then
        mockMvc.perform(post("/v1/task/createTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(3L)))
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.content", is("Test")));

    }

    @Test
    public void shouldUpdateTask() throws Exception {
        when(dbService.saveTask(any())).thenReturn(task);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(task);

        //When & Then
        mockMvc.perform(post("/v1/task/updateTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(3L)))
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.content", is("Test")));


    }

    @Test
    public void shouldDeleteTask() throws Exception {
        //When & Then
        mockMvc.perform(post("/v1/task/deleteTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .sessionAttr("taskId", 3L))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldReturnTasks() throws Exception {
        when(dbService.saveTask(any())).thenReturn(task);

        //When & Then
        mockMvc.perform(post("/v1/task/getTask")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8"))
                .andExpect(jsonPath("$.id", is(3L)))
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.content", is("Test")));

    }

    @Test
    public void shouldReturnTaskDto() throws Exception {
        List<Task> tasks = new ArrayList<>();
        tasks.add(task);

        when(dbService.getAllTasks()).thenReturn(tasks);
        //When & Then
        mockMvc.perform(post("/v1/task/getTasks")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(3L)))
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.content", is("Test")));
    }
}