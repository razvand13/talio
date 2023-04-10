package server.api;

import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class BoardControllerTest {
    TestBoardRepository repo;
    BoardController controller;
    @BeforeEach
    public void setup() {
        repo = new TestBoardRepository();
        controller = new BoardController(repo);
    }

    @Test
    public void cannotAddNullBoard() {
        var actual = controller.addBoard(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }
    @Test
    public void canAddGoodBoard() {
        var actual = controller.addBoard(new Board("name"));
        assertEquals(OK, actual.getStatusCode());

    }

    @Test
    public void cantAddDuplicateTest(){
        Board b1 = new Board("name");
        Board b2 = new Board("name");
        b2.id = b1.id;
        var first = controller.addBoard(b1);
        assertEquals(OK, first.getStatusCode());
        var second = controller.addBoard(b2);
        assertEquals(BAD_REQUEST, second.getStatusCode());

    }

    @Test
    public void databaseIsUsed() {
        controller.addBoard(new Board("name"));
        assertTrue(repo.calledMethods.contains("save"));
    }

    @Test
    public void getAllTest() {
        Board myBoard0 = new Board("name");
        myBoard0.id = 0;
        Board myBoard1 = new Board("name");
        myBoard1.id = 1;
        controller.addBoard(myBoard0);
        controller.addBoard(myBoard1);

        List<Board> expected = new ArrayList<Board>(Arrays.asList(myBoard0,myBoard1));

        assertEquals(expected, controller.getAllBoards());

    }

    @Test
    public void getByIdTest() {
        Board myBoard = new Board("name");
        myBoard.id = 0;
        controller.addBoard(myBoard);
        assertEquals(myBoard, controller.getBoardById(0).getBody());
    }

    @Test
    public void noBoardByIdTest(){
        assertEquals(BAD_REQUEST, controller.getBoardById(1).getStatusCode());
    }

    @Test
    public void addMessageTest(){
        Board b1 = new Board("name");
        Board b2 = controller.addMessage(b1);
        assertEquals(b1, b2);
    }

    @Test
    public void removeBoardTest(){
        Board b1 = new Board("name");
        Board b2 = controller.removeBoard(b1);
        assertEquals(b1, b2);
    }

    @Test
    public void deleteByIdTest() {
        Board myBoard0 = new Board("name");
        myBoard0.id = 0;
        Board myBoard1 = new Board("name");
        myBoard1.id = 1;
        controller.addBoard(myBoard0);
        controller.addBoard(myBoard1);

        controller.deleteById(1);

        assertEquals(BAD_REQUEST, controller.getById(1).getStatusCode());
        assertEquals(OK, controller.getById(0).getStatusCode());
    }

        @Test
    public void deleteAllTest() {
        Board myBoard0 = new Board("name");
        myBoard0.id = 0;
        Board myBoard1 = new Board("name");
        myBoard1.id = 1;
        controller.addBoard(myBoard0);
        controller.addBoard(myBoard1);

        controller.deleteAll();
        assertEquals(BAD_REQUEST, controller.getById(0).getStatusCode());
        assertEquals(BAD_REQUEST, controller.getById(1).getStatusCode());
    }

//    @Test
//    public void deleteByIdTest() {
//        Board myBoard0 = new Board("name");
//        myBoard0.id = 0;
//        Board myBoard1 = new Board("name");
//        myBoard1.id = 1;
//        controller.addBoard(myBoard0);
//        controller.addBoard(myBoard1);
//
//        controller.deleteBById(1);
//
//        assertEquals(BAD_REQUEST, controller.getById(1).getStatusCode());
//        assertEquals(OK, controller.getById(0).getStatusCode());
//    }
//
//    @Test
//    public void deleteAllTest() {
//        Board myBoard0 = new Board("name");
//        myBoard0.id = 0;
//        Board myBoard1 = new Board("name");
//        myBoard1.id = 1;
//        controller.add(myBoard0);
//        controller.add(myBoard1);
//
//        controller.deleteAll();
//        assertEquals(BAD_REQUEST, controller.getById(0).getStatusCode());
//        assertEquals(BAD_REQUEST, controller.getById(1).getStatusCode());
//    }

    @Test
    public void getMostRecentTest() {
        Board myBoard0 = new Board("name0");
        myBoard0.id = 0;
        Board myBoard1 = new Board("name1");
        myBoard1.id = 1;
        Board myBoard2 = new Board("name2");
        myBoard2.id = 2;
        controller.addBoard(myBoard0);
        assertEquals(myBoard0, controller.getMostRecent());
        controller.addBoard(myBoard1);
        assertEquals(myBoard1, controller.getMostRecent());
        controller.addBoard(myBoard2);
        assertEquals(myBoard2, controller.getMostRecent());
    }
}
