package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
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
//        list.cards = cards;
        String expected = "ListOfCards: id = 9223372036854775807, title = title, cards =" ;
//                + "\nCard: id = 9223372036854775807, title = title;";
        assertEquals(expected, list.toString());
    }

    @Test
    void setListOfCardsTest() {
        String name = "abc";
        ListOfCards l = new ListOfCards("list");
        ListOfCards l2 = new ListOfCards("list2");
        ListOfCards l3 = new ListOfCards("abc");
        l3.setTitle("list3");
        l.setTitle(name);
        l2.setTitle(name);
        assertEquals(l.title, l2.title);
        assertNotEquals(l.title, l3.title);
        assertNotEquals(l2.title, l3.title);
    }

    @Test
    void getListsOfCardsTest() {
        ListOfCards l = new ListOfCards("list");
        assertNotEquals(l.getTitle(), "list2");
        assertEquals(l.getTitle(), "list");
    }
}