package com.crud.tasks.trello.client;

import com.crud.tasks.config.TrelloConfig;
import com.crud.tasks.domain.*;
import jdk.nashorn.internal.runtime.regexp.joni.exception.SyntaxException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {
    @InjectMocks
    private TrelloClient trelloClient;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private TrelloConfig trelloConfig;

    @Before
    public void init() {
        //given
        when(trelloConfig.getEndPoint()).thenReturn("http://test.com");
        when(trelloConfig.getKey()).thenReturn("test");
        when(trelloConfig.getToken()).thenReturn("test");
    }

    @Test
    public void shouldFetchTrelloBoard() throws URISyntaxException {

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());
        URI url = new URI("http://test.com/members/tojsterr/boards?key=test&token=test&fields=name,id&lists=all");
        when(restTemplate.getForObject(url, TrelloBoardDto[].class)).thenReturn(trelloBoards);
        //when
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }

    @Test
    public void shouldCreateCard() throws URISyntaxException {
        //Given
        Badges badges = new Badges(2, new AttachmentsByType(new Trello(2, 3)));
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test task", "Test Description", "top", "test_id");
        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");
        CreatedTrelloCard createdTrelloCard = new CreatedTrelloCard("1", "Test task", "http://trello.com", badges);
        when(restTemplate.postForObject(uri, null, CreatedTrelloCard.class)).thenReturn(createdTrelloCard);

        //When
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);

        //Then
        assertEquals("1", newCard.getId());
        assertEquals("Test task", newCard.getName());
        assertEquals("http://trello.com", newCard.getShortUrl());
        assertEquals(badges, newCard.getBadges());

    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {
        // Given
        URI uri = new URI("http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id");
        when(restTemplate.getForObject(uri, TrelloCardDto[].class)).thenReturn(null);

        //When
            TrelloCardDto[] createdTrelloCards = restTemplate.getForObject(uri, TrelloCardDto[].class);
            if (createdTrelloCards==null){
                createdTrelloCards = new TrelloCardDto[0];
            }


        //Then

        assertEquals(0 ,createdTrelloCards.length);
    }

}