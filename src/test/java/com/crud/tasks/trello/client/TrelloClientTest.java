package com.crud.tasks.trello.client;

import com.crud.tasks.config.TrelloConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.mapper.TrelloMapper;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TrelloClientTest {
    @InjectMocks
    private TrelloClient trelloClient;
    @InjectMocks
    private TrelloFacade trelloFacade;
    @InjectMocks
    private TrelloMapper trelloMapper;
    @Mock
    private RestTemplate restTemplate;
    @Mock
    private TrelloConfig trelloConfig;
    private static final String URL_ID_LST = "http://test.com/cards?key=test&token=test&name=Test%20task&desc=Test%20Description&pos=top&idList=test_id";
    private static final String URL_LISTS_ALL = "http://test.com/members/tojsterr/boards?key=test&token=test&fields=name,id&lists=all";

    @Before
    public void init() throws URISyntaxException {
        //given
        when(trelloConfig.getEndPoint()).thenReturn("http://test.com");
        when(trelloConfig.getKey()).thenReturn("test");
        when(trelloConfig.getToken()).thenReturn("test");
    }

    @Test
    public void shouldFetchTrelloBoard() throws URISyntaxException {

        TrelloBoardDto[] trelloBoards = new TrelloBoardDto[1];
        trelloBoards[0] = new TrelloBoardDto("test_id", "test_board", new ArrayList<>());
        when(restTemplate.getForObject(new URI(URL_LISTS_ALL), TrelloBoardDto[].class)).thenReturn(trelloBoards);
        //when
        List<TrelloBoardDto> fetchedTrelloBoards = trelloClient.getTrelloBoards();

        //then
        assertEquals(1, fetchedTrelloBoards.size());
        assertEquals("test_id", fetchedTrelloBoards.get(0).getId());
        assertEquals("test_board", fetchedTrelloBoards.get(0).getName());
        assertEquals(new ArrayList<>(), fetchedTrelloBoards.get(0).getLists());
    }
//s
    @Test
    public void shouldCreateCard() throws URISyntaxException {
        //Given
        Badges badges = new Badges(2, new AttachmentsByType(new Trello(2, 3)));
        TrelloCardDto trelloCardDto = new TrelloCardDto("Test task", "Test Description", "top", "test_id");
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("1", "Test task", "http://trello.com", badges);
        when(restTemplate.postForObject(new URI(URL_ID_LST), null, CreatedTrelloCardDto.class)).thenReturn(createdTrelloCardDto);

        //When
        CreatedTrelloCardDto newCard = trelloClient.createNewCard(trelloCardDto);

        //Then
        assertEquals("1", newCard.getId());
        assertEquals("Test task", newCard.getName());
        assertEquals("http://trello.com", newCard.getShortUrl());
        assertEquals(badges, newCard.getBadges());

    }

    @Test
    public void shouldReturnEmptyList() throws URISyntaxException {
        // Given
        when(restTemplate.getForObject(new URI(URL_ID_LST), TrelloCardDto[].class)).thenReturn(null);


        //When
        TrelloCardDto[] createdTrelloCards = restTemplate.getForObject(new URI(URL_ID_LST), TrelloCardDto[].class);
        if (createdTrelloCards == null) {
            createdTrelloCards = new TrelloCardDto[0];
        }


        //Then

        assertEquals(0, createdTrelloCards.length);
    }

    @Test
    public void shouldMapToTrelloBoardList() {
        //Given
        List<TrelloBoardDto> trelloBoardDto = new ArrayList<>();
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("Test", "test", false));
        trelloListDtos.add(new TrelloListDto("Test", "test1", false));
        trelloListDtos.add(new TrelloListDto("Test", "test2", false));
        TrelloBoardDto trelloBoardDto1 = new TrelloBoardDto("test3", "test", trelloListDtos);
        trelloBoardDto.add(trelloBoardDto1);

        //When

        List<TrelloBoard> trelloBoards = trelloMapper.mapToBoards(trelloBoardDto);

        // Then

        assertEquals("test", trelloBoards.get(0).getName());

    }

    @Test
    public void shouldMapToTrelloBoardDtosList() {
        List<TrelloBoard> trelloBoard = new ArrayList<>();
        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("Test", "test", false));
        trelloList.add(new TrelloList("Test", "test1", false));
        trelloList.add(new TrelloList("Test", "test2", false));
        TrelloBoard trelloBoard1 = new TrelloBoard("test3", "test", trelloList);
        trelloBoard.add(trelloBoard1);
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto();

        //When

        List<TrelloBoardDto> trelloBoardDtos = trelloMapper.mapToBoardsDto(trelloBoard);

        //Then
        assertEquals("test3", trelloBoardDtos.get(0).getId());
        assertEquals("test3", trelloBoardDtos.get(0).getId());
        assertEquals("test", trelloBoardDtos.get(0).getName());
        assertEquals("test", trelloBoardDtos.get(0).getLists().get(0).getName());
        assertEquals("Test", trelloBoardDtos.get(0).getLists().get(0).getId());
        assertFalse(trelloBoardDtos.get(0).getLists().get(0).isClosed());
        assertNull(trelloBoardDto.getId());
    }

    @Test
    public void shouldMapToTrelloCardDto() {

        //Given
        TrelloCard trelloCard = new TrelloCard("test", "test1", "2", "13");

        //When
        TrelloCardDto cardDto = trelloMapper.maptoCardDto(trelloCard);
        //Then
        assertEquals("test", cardDto.getName());
        assertEquals("test1", cardDto.getDescription());
        assertEquals("2", cardDto.getPos());
        assertEquals("13", cardDto.getListId());
    }

    @Test
    public void shouldMapToTrelloCard() {
        //Given
        TrelloCardDto trelloCard = new TrelloCardDto("test", "test1", "2", "13");

        //When
        TrelloCard cardDto = trelloMapper.mapToCard(trelloCard);
        //Then
        assertEquals("test", cardDto.getName());
        assertEquals("test1", cardDto.getDescription());
        assertEquals("2", cardDto.getPos());
        assertEquals("13", cardDto.getListId());

    }

    @Test
    public void shouldMapListDtosToTrelloList() {
        //Given
        List<TrelloListDto> trelloListDtos = new ArrayList<>();
        trelloListDtos.add(new TrelloListDto("Test", "test", false));
        trelloListDtos.add(new TrelloListDto("Test", "test1", false));
        trelloListDtos.add(new TrelloListDto("Test", "test2", false));

        //When
        List<TrelloList> trelloList = trelloMapper.mapToList(trelloListDtos);

        //Then

        assertEquals("Test", trelloList.get(0).getId());
        assertEquals("test", trelloList.get(0).getName());
        assertFalse(trelloList.get(0).isClosed());

    }

    @Test
    public void shouldMapListToTrelloListDto() {
        //Given

        List<TrelloList> trelloList = new ArrayList<>();
        trelloList.add(new TrelloList("Test", "test", false));
        trelloList.add(new TrelloList("Test", "test1", false));
        trelloList.add(new TrelloList("Test", "test2", false));

        //Given
        List<TrelloListDto> trelloListDtos = trelloMapper.mapToListDto(trelloList);

        //Then
        assertEquals("Test", trelloListDtos.get(0).getId());
        assertEquals("test", trelloListDtos.get(0).getName());
        assertFalse(trelloListDtos.get(0).isClosed());
    }

}


