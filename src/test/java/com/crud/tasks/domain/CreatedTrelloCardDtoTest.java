package com.crud.tasks.domain;

import org.junit.Test;

import static org.junit.Assert.*;

public class CreatedTrelloCardDtoTest {
    @Test
    public void createCardDtoTest() {
        Trello trello = new Trello(3, 3);
        Trello trello1 = new Trello();
        AttachmentsByType attachmentsByType = new AttachmentsByType(trello);
        AttachmentsByType attachmentsByType1 = new AttachmentsByType();
        Badges badges1 = new Badges();
        Badges badges = new Badges(3, attachmentsByType);
        CreatedTrelloCardDto createdTrelloCardDto1 = new CreatedTrelloCardDto();
        CreatedTrelloCardDto createdTrelloCardDto = new CreatedTrelloCardDto("test", "test", "test", badges);

        int board = trello.getBoard();
        int card = trello.getCard();

        Trello trello2 = attachmentsByType.getTrello();

        AttachmentsByType attachmentsByType2 = badges.getAttachmentsByType();
        int votes = badges.getVotes();
        Badges badges2 = createdTrelloCardDto.getBadges();
        String id = createdTrelloCardDto.getId();
        String name = createdTrelloCardDto.getName();
        String shortUrl = createdTrelloCardDto.getShortUrl();

        assertEquals(3 , votes , 0);
        assertEquals("test" , id);
        assertEquals("test" , name);
        assertEquals("test" , shortUrl);
    }
}