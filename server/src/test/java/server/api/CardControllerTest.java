package server.api;

import commons.Board;
import commons.Card;
import commons.ListOfCards;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class CardControllerTest {
    TestListRepository listRepo;
    TestCardRepository cardRepo;
    CardController controller;
    ListOfCards thisList;

    @BeforeEach
    public void setup() {
        listRepo = new TestListRepository();
        cardRepo = new TestCardRepository();
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

    @Test
    public void getAllTest() {
        ListOfCards myList = new ListOfCards("name", new Board("name", "colour"));
        Card card0 = new Card("name", "description", "colour", myList);
        Card card1 = new Card("name", "description", "colour", myList);
        card0.id = 0;
        card1.id = 1;
        controller.add(card0, myList.id);
        controller.add(card1, myList.id);

        List expected = new ArrayList<Card>(Arrays.asList(card0, card1));

        assertEquals(expected, controller.getAll());
    }

    @Test
    public void getByIdTest(){
        ListOfCards myList = new ListOfCards("name", new Board("name", "colour"));
        Card card0 = new Card("name", "description", "colour", myList);
        card0.id = 0;

        controller.add(card0, myList.id);

        assertEquals(card0, controller.getById(0).getBody());
    }

    @Test
    public void deleteByIdTest() {
        ListOfCards myList = new ListOfCards("name", new Board("name", "colour"));
        Card card0 = new Card("name", "description", "colour", myList);
        Card card1 = new Card("name", "description", "colour", myList);
        card0.id = 0;
        card1.id = 1;
        controller.add(card0, myList.id);
        controller.add(card1, myList.id);

        controller.deleteById(1);

        assertEquals(BAD_REQUEST, controller.getById(1).getStatusCode());
        assertEquals(OK, controller.getById(0).getStatusCode());
    }

    @Test
    public void deleteAllTest(){
        ListOfCards myList = new ListOfCards("name", new Board("name", "colour"));
        Card card0 = new Card("name", "description", "colour", myList);
        Card card1 = new Card("name", "description", "colour", myList);
        card0.id = 0;
        card1.id = 1;
        controller.add(card0, myList.id);
        controller.add(card1, myList.id);

        controller.deleteAll();

        assertEquals(BAD_REQUEST, controller.getById(1).getStatusCode());
        assertEquals(BAD_REQUEST, controller.getById(0).getStatusCode());
    }
}
