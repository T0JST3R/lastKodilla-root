package com.crud.tasks.controller;

import com.crud.tasks.domain.*;
import com.crud.tasks.service.DbService;
import com.crud.tasks.trello.facade.TrelloFacade;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatcher;
import org.mockito.internal.progress.ArgumentMatcherStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(TrelloController.class)
public class TrelloControllerTest {
    Task task = new Task(3L, "Test", "Test");
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TrelloFacade trelloFacade;
    @MockBean
    private DbService dbService;

    @Test
    public void shouldFetchEmptyTrelloBoards() throws Exception {
        //Given
        List<TrelloBoardDto> boardDtoList = new ArrayList<>();
        when(trelloFacade.fetchTrelloBoards()).thenReturn(boardDtoList);

        //When

        mockMvc.perform(get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchTrelloBoards() throws Exception {
        List<TrelloListDto> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloListDto("1", "test", false));
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(new TrelloBoardDto("1", "test", trelloLists));

        when(trelloFacade.fetchTrelloBoards()).thenReturn(trelloBoardDtos);
        mockMvc.perform(get("/v1/trello/getTrelloBoards").contentType(MediaType.APPLICATION_JSON))
                //Trello board fields
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is("1")))
                .andExpect(jsonPath("$[0].name", is("test")))
                //Trello list fields
                //       .andExpect(jsonPath("$.lists", hasSize(1)))
                .andExpect(jsonPath("$[0].lists[0].id", is("1")))
                .andExpect(jsonPath("$[0].lists[0].name", is("test")))
                .andExpect(jsonPath("$[0].lists[0].closed", is(false)));
    }

    @Test
    public void shouldCreateTrelloCard() throws Exception {
        //Given
        TrelloCardDto trelloCardDto = new TrelloCardDto(
                "Test",
                "test dsc",
                "top",
                "1"
        );
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto(
                "323",
                "Test",
                "http://test.com"
                , new Badges(2, new AttachmentsByType(new Trello(2, 3)))
        );
        when(trelloFacade.createCard(any())).thenReturn(createdTrelloCardDto);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(trelloCardDto);

        //When & Then

        mockMvc.perform(post("/v1/trello/createTrelloCard")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is("323")))
                .andExpect(jsonPath("$.name", is("Test")))
                .andExpect(jsonPath("$.shortUrl", is("http://test.com")));


    }

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