package server.api;

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
        thisList = new ListOfCards("name");
        thisList.id = 1;
        listRepo.lists.add(thisList);
    }

    @Test
    public void cannotAddNullCard(){
        var actual = controller.add(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void cannotAddToNonExistentList() {

        var actual = controller.add(new Card("name",
                new ListOfCards("name")));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void canAddGoodCard() {
        var actual = controller.add(new Card("name",  thisList));
        assertEquals(OK, actual.getStatusCode());
    }

    @Test
    public void databaseIsUsed(){
        controller.add(new Card("name",  thisList));
        assertTrue(cardRepo.calledMethods.contains("save"));
    }

    @Test
    public void getAllTest() {

        Card card0 = new Card("name",  thisList);
        Card card1 = new Card("name",  thisList);
        card0.id = 0;
        card1.id = 1;
        controller.add(card0);
        controller.add(card1);

        List expected = new ArrayList<Card>(Arrays.asList(card0, card1));

        assertEquals(expected, controller.getAll());
    }

    @Test
    public void getByIdTest(){
        Card card0 = new Card("name",  thisList);
        card0.id = 0;

        controller.add(card0);

        assertEquals(card0, controller.getById(0).getBody());
    }

    @Test
    public void deleteByIdTest() {

        Card card0 = new Card("name",  thisList);
        Card card1 = new Card("name",  thisList);
        card0.id = 0;
        card1.id = 1;
        controller.add(card0);
        controller.add(card1);

        controller.deleteById(1);

        assertEquals(BAD_REQUEST, controller.getById(1).getStatusCode());
        assertEquals(OK, controller.getById(0).getStatusCode());
    }

    @Test
    public void deleteAllTest(){
        Card card0 = new Card("name",  thisList);
        Card card1 = new Card("name",  thisList);
        card0.id = 0;
        card1.id = 1;
        controller.add(card0);
        controller.add(card1);

        controller.deleteAll();

        assertEquals(BAD_REQUEST, controller.getById(1).getStatusCode());
        assertEquals(BAD_REQUEST, controller.getById(0).getStatusCode());
    }

    @Test
    public void addMessageTest(){
        Card card = new Card("name",  thisList);
        card.id = 0;
        controller.addMessage(card);
        assertEquals(OK, controller.getById(0).getStatusCode());
        assertTrue(cardRepo.calledMethods.contains("save"));
    }
}
