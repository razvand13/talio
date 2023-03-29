package server.api;

import commons.Board;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void databaseIsUsed(){
        controller.add(new Board("name", "colour"));
        assertTrue(repo.calledMethods.contains("save"));
    }



}
