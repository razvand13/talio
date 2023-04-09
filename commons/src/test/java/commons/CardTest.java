package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

class CardTest {

    private Card card;
    private Card sameCard;
    private Card equalCard;
    private Card diffCard;
    private long myId;
    private ListOfCards list1;
    private ListOfCards list2;
    private int myPosition;
    @BeforeEach
    void setup(){
        myId = Long.MAX_VALUE;
        list1 = new ListOfCards("list1");
        list2 = new ListOfCards("list2");
        card = new Card("title",list1);
        sameCard = card;
        equalCard = new Card("title",list1);
        diffCard = new Card("diffTitle", list2);
        card.id = myId;
        equalCard.id = myId;
        diffCard.id = myId;
        myPosition = 5;
        card.position = myPosition;
        equalCard.position = myPosition;
        diffCard.position = myPosition;
        Card c = new Card("TITLE");
    }

    @Test
    void testConstructor(){
        assertEquals("title", card.title);
        assertEquals(list1, card.listOfCards);
        assertEquals(myId, card.id);
        assertEquals(myPosition, card.position);
    }


    @Test
    void testNotNull(){
        assertNotNull(card);
    }

    @Test
    void testEqualsSame(){
        assertEquals(card, sameCard);
    }

    @Test
    void testEquals() {
        assertEquals(card, equalCard);
    }

    @Test
    void testNotEqual(){
        assertNotEquals(card, diffCard);
    }

    @Test
    void testHashCode() {
        assertEquals(card.hashCode(), equalCard.hashCode());
    }

    @Test
    void testHashCodeSame(){
        assertEquals(card.hashCode(), sameCard.hashCode());
    }

    @Test
    void testToString() {
        card.position = 1;
        String expected = "Card: id = 9223372036854775807, title = title, position = 1";
        assertEquals(expected, card.toString());
    }

    @Test
    void testSetter(){
        ListOfCards l = new ListOfCards("name");
        ListOfCards l2 = new ListOfCards("name2");
        ListOfCards l3 = new ListOfCards("name");
        Card c = new Card("TITLE", l);
        Card c2 = new Card("TITLE", l);
        Card c3 = new Card("TITLE2",l);
        c.setCard("title", l3);
        c2.setCard("title", l3);
        c3.setCard("T", l);
        assertEquals(c, c2);
        assertNotEquals(c, c3);
        assertNotEquals(c2, c3);
    }

    @Test
    void testSetter2(){
        Card c = new Card("TITLE");
        Card c2 = new Card("TITLE");
        Card c3 = new Card("TITLE2");
        c2.setCard("title");
        c.setCard("title");
        c3.setCard("TITLE");
        assertEquals(c, c2);
        assertNotEquals(c, c3);
        assertNotEquals(c2, c3);
    }

//
}