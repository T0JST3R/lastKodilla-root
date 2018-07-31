package com.crud.tasks.trello.validator;

import com.crud.tasks.domain.TrelloBoard;
import com.crud.tasks.domain.TrelloCard;
import com.crud.tasks.domain.TrelloList;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
@RunWith(MockitoJUnitRunner.class)
public class TrelloValidatorTest {
    @InjectMocks
    private TrelloValidator trelloValidator;
    @Test
    public void shouldReturnOneElementList() {
        List<TrelloBoard> trelloBoards = new ArrayList<>();
        List<TrelloList> trelloLists = new ArrayList<>();
        trelloLists.add(new TrelloList("1" , "test" , false));
        trelloBoards.add(new TrelloBoard("1" , "NOT_A_TEST" , trelloLists));

        List<TrelloBoard> boards = trelloValidator.validateTrelloBoards(trelloBoards);

        assertEquals(1 , boards.size());

    }
    @Test
    public void shouldLogSomeInfo(){
        trelloValidator.validateCard(new TrelloCard("test" , "test" , "test" , "1"));
    }

}