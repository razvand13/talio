package server.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import DataStructures.Card;
import DataStructures.Tag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import commons.Person;
import commons.Quote;

public class CardControllerTest {

    public int nextInt;
    private MyRandom random;
    private TestCardRepository repo;

    private CardController sut;

    @BeforeEach
    public void setup() {
        random = new MyRandom();
        repo = new TestCardRepository();
        sut = new CardController(random, repo);
    }

    @Test
    public void cannotAddNullPerson() {
        var actual = sut.add(getCard(null));
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }


    @Test
    public void databaseIsUsed() {
        sut.add(getCard("q1"));
        repo.calledMethods.contains("save");
    }

    private static Card getCard(String q) {
        List<String> testTasks = new ArrayList<>();
        List<Tag> testTags = new ArrayList<>();
        return new Card("test card", "description", testTasks, testTags, "blue");
    }

    @SuppressWarnings("serial")
    public class MyRandom extends Random {

        public boolean wasCalled = false;

        @Override
        public int nextInt(int bound) {
            wasCalled = true;
            return nextInt;
        }
    }
}