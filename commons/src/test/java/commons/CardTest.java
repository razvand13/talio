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
        String expected = "Card: id = 9223372036854775807, title = title";
        assertEquals(expected, card.toString());
    }
}