package commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    @Test
    void constructorTest(){
        Board b1 = new Board("boardTitle");
        assertNotNull(b1);
    }

    @Test
    void emptyConstructorTest(){
        Board b1 = new Board();
        assertNotNull(b1);
    }

    @Test
    void hashCodeTest(){
        Board b1 = new Board("boardTitle");
        Board b2 = b1;
        assertEquals(b1.hashCode(), b2.hashCode());

    }

    @Test
    void toStringTest(){
        Board b1 = new Board("boardTitle");
        b1.id = 12345;
        assertEquals("Board: id=12345, title=boardTitle", b1.toString(), b1.toString());
    }

    @Test
    void equalsTest(){
        Board b1 = new Board("boardTitle");
        Board b2 = b1;
        assertTrue(b1.equals(b2));
    }

    @Test
    void diffTitleTest(){
        Board b1 = new Board("title1");
        Board b2 = new Board("title2");
        b2.id = b1.id;
        assertNotEquals(b1, b2);
    }

    @Test
    void diffIDTest(){
        Board b1 = new Board("title1");
        Board b2 = new Board("title1");
        b1.id = b2.id+1;
        assertNotEquals(b1, b2);
    }






}