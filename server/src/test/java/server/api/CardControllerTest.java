package server.api;

import commons.Board;
import commons.Card;
import commons.ListOfCards;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class CardControllerTest {
    TestListRepository listRepo;
    TestCardRepositrory cardRepo;
    CardController controller;
    ListOfCards thisList;

    @BeforeEach
    public void setup() {
        listRepo = new TestListRepository();
        cardRepo = new TestCardRepositrory();
        controller = new CardController(cardRepo, listRepo);
        thisList = new ListOfCards("name", new Board("name", "colour"));
        thisList.id = 0;
        listRepo.lists.add(thisList);
    }

    @Test
    public void cannotAddNullCard(){
        var actual = controller.add(null, 0);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void cannotAddToNonExistentList() {
        var actual = controller.add(new Card("name", "description", "colour",
                new ListOfCards("name",
                        new Board("name", "colour"))),
                1);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void canAddGoodCard() {
        var actual = controller.add(new Card("name", "description", "colour", thisList), 0);
        assertEquals(OK, actual.getStatusCode());
    }

    @Test
    public void databaseIsUsed(){
        controller.add(new Card("name", "description", "colour", thisList), 0);
        assertTrue(cardRepo.calledMethods.contains("save"));
    }
}
