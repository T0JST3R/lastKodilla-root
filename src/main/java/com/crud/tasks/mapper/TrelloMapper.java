package com.crud.tasks.mapper;

import com.crud.tasks.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class TrelloMapper {



    public List<TrelloBoard> mapToBoards(List<TrelloBoardDto> trelloBoardsDto) {
        return trelloBoardsDto.stream()
                .map(trelloBoardDto -> new TrelloBoard(trelloBoardDto.getId(), trelloBoardDto.getName(), mapToList(trelloBoardDto.getLists())))
                .collect(toList());
    }

    public List<TrelloBoardDto> mapToBoardsDto(final List<TrelloBoard> boards){
       return boards.stream()
                .map(trelloBoard -> new TrelloBoardDto(trelloBoard.getId() ,trelloBoard.getName() , mapToListDto(trelloBoard.getLists())))
                .collect(toList());

    }

    public List<TrelloList> mapToList(final List<TrelloListDto> dtoList) {
        return dtoList.stream()
                .map(trelloDtoList -> new TrelloList(trelloDtoList.getId(), trelloDtoList.getName(), trelloDtoList.isClosed()))
                .collect(toList());
    }

    public List<TrelloListDto> mapToListDto(final List<TrelloList> lists) {
        return lists.stream()
                .map(trelloList -> new TrelloListDto(trelloList.getId(), trelloList.getName(), trelloList.isClosed()))
                .collect(toList());
    }
    public TrelloCardDto maptoCardDto(final TrelloCard trelloCard){
        return new TrelloCardDto(trelloCard.getName() , trelloCard.getDescription() , trelloCard.getPos() , trelloCard.getListId());

    }
    public TrelloCard mapToCard(final TrelloCardDto trelloCardDto){
        return new TrelloCard(trelloCardDto.getName() , trelloCardDto.getDescription() , trelloCardDto.getPos() , trelloCardDto.getListId());
    }
}
