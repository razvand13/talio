package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private Card card;
    private Card sameCard;
    private Card equalCard;
    private Card diffCard;
    private long myId;
    private ListOfCards list1;
    private ListOfCards list2;

    private Board board1;
    private Board board2;

    @BeforeEach
    void setup(){
        myId = Integer.MAX_VALUE;
        board1 = new Board("title1", "color1");
        board2 = new Board("title2", "color2");
        list1 = new ListOfCards("list1", board1);
        list2 = new ListOfCards("list2", board2);
        card = new Card("title", "desc", "color",list1);
        sameCard = card;
        equalCard = new Card("title", "desc", "color",list1);
        diffCard = new Card("diffTitle", "diffDesc", "diffColor", list2);
        card.id = myId;

    }

    @Test
    void testEquals() {
    }

    @Test
    void testHashCode() {
    }

    @Test
    void testToString() {
    }
}