package com.crud.tasks.trello.client;

import com.crud.tasks.domain.CreatedTrelloCard;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TrelloClient {
    @Autowired
    private RestTemplate restTemplate;
    @Value("${trello.api.endpoint.prod}")
    private String trelloApiEndpoint;
    @Value("${trello.app.key}")
    private String trelloAppKey;
    @Value("${trello.app.token}")
    private String trelloToken;
    @Value("${trello.app.username}")
    private String trelloUserName;

    private URI urlConstructor(){
        return UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/members/tojsterr/boards")
                .queryParam("key", trelloAppKey)
                .queryParam("token", trelloToken)
                .queryParam("fields", "name,id")
                .queryParam("lists" , "all").build().encode().toUri();
    }

    public List<TrelloBoardDto> getTrelloBoards() {


        TrelloBoardDto[] boardsResponse = restTemplate.getForObject(urlConstructor(), TrelloBoardDto[].class);
        if (boardsResponse != null) {
            return Arrays.asList(boardsResponse);
        }
        return new ArrayList<>();
    }

    public CreatedTrelloCard createNewCard(TrelloCardDto trelloCardDto){
        URI url = UriComponentsBuilder.fromHttpUrl(trelloApiEndpoint + "/cards")
                .queryParam("key" , trelloAppKey)
                .queryParam("token" , trelloToken)
                .queryParam("name" , trelloCardDto.getName())
                .queryParam("desc" , trelloCardDto.getDescription())
                .queryParam("pos" , trelloCardDto.getPos())
                .queryParam("idList" , trelloCardDto.getListId())
                .build().encode().toUri();
        return restTemplate.postForObject(url, null , CreatedTrelloCard.class);
    }
}
