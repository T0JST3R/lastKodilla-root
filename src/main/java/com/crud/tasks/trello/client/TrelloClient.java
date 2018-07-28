package com.crud.tasks.trello.client;

import com.crud.tasks.config.TrelloConfig;
import com.crud.tasks.domain.CreatedTrelloCardDto;
import com.crud.tasks.domain.TrelloBoardDto;
import com.crud.tasks.domain.TrelloCardDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class TrelloClient {
    @Autowired
    TrelloConfig trelloConfig;
    Logger LOGGER = LoggerFactory.getLogger(TrelloClient.class);
    @Autowired
    private RestTemplate restTemplate;

    private URI urlConstructor() {
        return UriComponentsBuilder.fromHttpUrl(trelloConfig.getEndPoint() + "/members/tojsterr/boards")
                .queryParam("key", trelloConfig.getKey())
                .queryParam("token", trelloConfig.getToken())
                .queryParam("fields", "name,id")
                .queryParam("lists", "all").build().encode().toUri();
    }

    public List<TrelloBoardDto> getTrelloBoards() {

        try {
            TrelloBoardDto[] boardsResponse = restTemplate.getForObject(urlConstructor(), TrelloBoardDto[].class);

            return Arrays.asList(boardsResponse);
        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }


    public CreatedTrelloCardDto createNewCard(TrelloCardDto trelloCardDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(trelloConfig.getEndPoint() + "/cards")
                .queryParam("key", trelloConfig.getKey())
                .queryParam("token", trelloConfig.getToken())
                .queryParam("name", trelloCardDto.getName())
                .queryParam("desc", trelloCardDto.getDescription())
                .queryParam("pos", trelloCardDto.getPos())
                .queryParam("idList", trelloCardDto.getListId())
                .build().encode().toUri();
        return restTemplate.postForObject(url, null, CreatedTrelloCardDto.class);
    }
}
