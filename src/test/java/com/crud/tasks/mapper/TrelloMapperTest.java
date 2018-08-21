package com.crud.tasks.mapper;

import com.crud.tasks.config.TrelloConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import com.crud.tasks.trello.facade.TrelloFacade;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
@RunWith(MockitoJUnitRunner.class)
public class TrelloMapperTest {
    @InjectMocks
    private TrelloMapper trelloMapper;
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
