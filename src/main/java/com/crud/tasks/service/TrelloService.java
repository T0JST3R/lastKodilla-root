package com.crud.tasks.service;

import com.crud.tasks.config.AdminConfig;
import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.Mail;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import com.crud.tasks.trello.client.TrelloClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.crud.tasks.domain.Mail.SUBJECT;
import static java.util.Optional.ofNullable;

@Service
public class TrelloService {
    @Autowired
    private TrelloClient trelloClient;
    @Autowired
    private SimpleEmailService emailService;
    @Autowired
    AdminConfig adminConfig;


    public List<TrelloBoardDto> fetchTrelloBoards(){
      return trelloClient.getTrelloBoards();
    }
    public CreatedTrelloCard createTrelloCard(final TrelloCardDto trelloCardDto){
        CreatedTrelloCard newCard = trelloClient.createNewCard(trelloCardDto);
        ofNullable(trelloCardDto).ifPresent(card -> emailService.send(new Mail(adminConfig.getAdminMail(), SUBJECT , "New Card " + card.getName() + " has been sent on your Trello account")));

        return newCard;
    }
}
