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
        var actual = controller.add(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }
    @Test
    public void canAddGoodBoard() {
        var actual = controller.add(new Board("name", "colour"));
        assertEquals(OK, actual.getStatusCode());

    }

    @Test
    public void databaseIsUsed() {
        controller.add(new Board("name", "colour"));
        assertTrue(repo.calledMethods.contains("save"));
    }

    @Test
    public void getAllTest() {
        Board myBoard0 = new Board("name", "colour");
        myBoard0.id = 0;
        Board myBoard1 = new Board("name", "colour");
        myBoard1.id = 1;
        controller.add(myBoard0);
        controller.add(myBoard1);

        List<Board> expected = new ArrayList<Board>(Arrays.asList(myBoard0,myBoard1));

        assertEquals(expected, controller.getAll());

    }

    @Test
    public void getByIdTest() {
        Board myBoard = new Board("name", "colour");
        myBoard.id = 0;
        controller.add(myBoard);
        assertEquals(myBoard, controller.getById(0).getBody());
    }

    @Test
    public void deleteByIdTest() {
        Board myBoard0 = new Board("name", "colour");
        myBoard0.id = 0;
        Board myBoard1 = new Board("name", "colour");
        myBoard1.id = 1;
        controller.add(myBoard0);
        controller.add(myBoard1);

        controller.deleteById(1);

        assertEquals(BAD_REQUEST, controller.getById(1).getStatusCode());
        assertEquals(OK, controller.getById(0).getStatusCode());
    }

    @Test
    public void deleteAllTest() {
        Board myBoard0 = new Board("name", "colour");
        myBoard0.id = 0;
        Board myBoard1 = new Board("name", "colour");
        myBoard1.id = 1;
        controller.add(myBoard0);
        controller.add(myBoard1);

        controller.deleteAll();
        assertEquals(BAD_REQUEST, controller.getById(0).getStatusCode());
        assertEquals(BAD_REQUEST, controller.getById(1).getStatusCode());
    }
}
