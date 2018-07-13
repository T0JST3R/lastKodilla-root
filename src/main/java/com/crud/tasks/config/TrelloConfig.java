package com.crud.tasks.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class TrelloConfig {
    @Value("${trello.app.username}")
    private String username;
    @Value("${trello.app.token}")
    private String token;
    @Value("${trello.app.key}")
    private String key;
    @Value("${trello.api.endpoint.prod}")
    private String endPoint;
}
