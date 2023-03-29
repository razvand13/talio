package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TagTest {

    private Tag tag;
    private Tag sameTag;
    private Tag equalTag;
    private Tag diffTag;
//    private Set<Tag> cardTags;
    private long myId;
    private Card card;
    private ListOfCards list;
    private Board board;

    @BeforeEach
    void setup(){
        myId = Integer.MAX_VALUE;
        board = new Board("title1", "color1");
        list = new ListOfCards("list1", board);
        card = new Card("title", "desc", "color", list);
//        cardTags = new HashSet<>();
//        cardTags.add(tag);
//        card.tags = cardTags;
        tag = new Tag("name", "color", card);
        sameTag = tag;
        equalTag = new Tag("name", "color", card);
        diffTag = new Tag("diffName", "diffColor", card);
        tag.id = myId;
        equalTag.id = myId;
        diffTag.id = myId;
    }

    @Test
    void testEqualsSame(){
        assertEquals(tag, sameTag);
    }

    @Test
    void testEquals() {
        assertEquals(tag, equalTag);
    }

    @Test
    void testNotEqual(){
        assertNotEquals(tag, diffTag);
    }

    @Test
    void testHashCode() {
        assertEquals(tag.hashCode(), equalTag.hashCode());
    }

    @Test
    void testHashCodeSame(){
        assertEquals(tag.hashCode(), sameTag.hashCode());
    }

    @Test
    void testToString() {
        //todo
    }
}