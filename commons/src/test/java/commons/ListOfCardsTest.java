package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListOfCardsTest {

    private ListOfCards list;
    private ListOfCards sameList;
    private ListOfCards diffList;
    private ListOfCards equalList;
    private long myId;

    @BeforeEach
    void setup(){
        myId = Long.MAX_VALUE;
        list = new ListOfCards("title");
        sameList = list;
        diffList = new ListOfCards("diffTitle");
        equalList = new ListOfCards("title");
        list.id = myId;
        diffList.id = myId;
        equalList.id = myId;
    }

    @Test
    void testNotNull(){
        assertNotNull(list);
    }

    @Test
    void testEqualsSame(){
        assertEquals(list, sameList);
    }

    @Test
    void testEquals() {
        assertEquals(list, equalList);
    }

    @Test
    void testNotEqual(){
        assertNotEquals(list, diffList);
    }

    @Test
    void testHashCodeSame(){
        assertEquals(list.hashCode(), sameList.hashCode());
    }

    @Test
    void testHashCode() {
        assertEquals(list.hashCode(), equalList.hashCode());
    }

    @Test
    void testToString() {
        List<Card> cards = new ArrayList<>();
        Card c = new Card("title", list);
        c.id = myId;
        cards.add(c);
        list.cards = cards;
        String expected = "ListOfCards: id = 9223372036854775807, title = title, cards =\n" +
                "Card: id = 9223372036854775807, title = title;";
        assertEquals(expected, list.toString());
    }
}