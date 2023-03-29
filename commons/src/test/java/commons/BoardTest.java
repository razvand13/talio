package commons;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BoardTest {

    private Board board;
    private Board sameBoard;
    private Board equalBoard;
    private Board diffBoard;
    // For testing simplicity we will use the same indices
    private long myId;

    @BeforeEach
    void setup(){
        myId = Integer.MAX_VALUE;
        board = new Board("title", "color");
        sameBoard = board;
        equalBoard = new Board("title", "color");
        diffBoard = new Board("another title", "another color");
        board.id = myId;
        sameBoard.id = myId;
        equalBoard.id = myId;
        diffBoard.id = myId;
    }

    @Test
    public void testNotNull(){
        assertNotNull(board);
    }

    @Test
    public void testEqualsSame(){
        assertEquals(board, sameBoard);
    }

    @Test
    void testEquals() {
        assertEquals(board, equalBoard);
    }

    @Test
    public void testNotEqual(){
        assertNotEquals(board, diffBoard);
    }

    @Test
    void testHashCodeSame() {
        assertEquals(board.hashCode(), sameBoard.hashCode());
    }

    @Test
    public void testHashCode(){
        assertEquals(board.hashCode(), equalBoard.hashCode());
    }

    @Test
    void testToString() {
        // todo
    }
}