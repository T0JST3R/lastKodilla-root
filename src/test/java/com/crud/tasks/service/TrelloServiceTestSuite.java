package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.*;
import com.crud.tasks.trello.client.TrelloClient;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TrelloServiceTestSuite {
    @Mock
    private TrelloClient trelloClient;
    @Mock
    AdminConfig adminConfig;
    @Mock
    SimpleEmailService emailService;
    @InjectMocks
    private TrelloService trelloService;
    private TrelloListDto trelloListDto = new TrelloListDto("List", "List", false);
    private List<TrelloListDto> trelloListDtos = new ArrayList<>();
    private TrelloCardDto trelloCardDto = new TrelloCardDto("Name", "Description", "Pos", "IdList");


    @Test
    public void shouldFetchTrelloBoard() {
        //Given
        trelloListDtos.add(trelloListDto);
        TrelloBoardDto trelloBoardDto = new TrelloBoardDto("id", "Name", trelloListDtos);
        List<TrelloBoardDto> trelloBoardDtos = new ArrayList<>();
        trelloBoardDtos.add(trelloBoardDto);
        when(trelloClient.getTrelloBoards()).thenReturn(trelloBoardDtos);

        //When
        List<TrelloBoardDto> boardDtoList = trelloService.fetchTrelloBoards();
        //Then
        assertEquals(1, boardDtoList.size(), 0);
    }


    @Test
    public void shouldCreateTrelloCard() {

        //Given
        Trello trello = new Trello(4, 5);
        AttachmentsByType attachmentsByType = new AttachmentsByType(trello);
        Badges badges = new Badges(5, attachmentsByType);
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("Id", "Name", "ShortUrl", badges);
        when(trelloClient.createNewCard(trelloCardDto)).thenReturn(createdTrelloCardDto);
        when(adminConfig.getAdminMail()).thenReturn("admin@admin.com");
        //When
        CreatedTrelloCardDto createdTrelloCardDto1 = trelloService.createTrelloCard(trelloCardDto);
        //Then

        assertEquals("Name", createdTrelloCardDto1.getName());
        assertEquals("Id", createdTrelloCardDto1.getId());
        assertEquals("ShortUrl", createdTrelloCardDto1.getShortUrl());
        assertEquals(4, createdTrelloCardDto1.getBadges().getAttachmentsByType().getTrello().getBoard());
        assertEquals(5, createdTrelloCardDto1.getBadges().getAttachmentsByType().getTrello().getCard());
        assertEquals(5, createdTrelloCardDto1.getBadges().getVotes());
    }
}
