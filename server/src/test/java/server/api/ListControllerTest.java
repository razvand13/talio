package server.api;

import commons.Board;
import commons.ListOfCards;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.OK;

public class ListControllerTest {
    TestBoardRepository boardRepo;
    TestListRepository listRepo;
    ListController controller;
    Board thisBoard;

    @BeforeEach
    public void setup() {
        boardRepo = new TestBoardRepository();
        listRepo = new TestListRepository();
        controller = new ListController(listRepo, boardRepo);
        thisBoard = new Board("name","colour");
        thisBoard.id = 0;
        boardRepo.boards.add(thisBoard);
    }

    @Test
    public void cannotAddNullList() {
        var actual = controller.add(null, 0);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void cannotAddToNonExistentBoard() {
        var actual = controller.add(new ListOfCards("name", new Board("name", "colour")), 1);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void canAddGoodList(){
        var actual = controller.add(new ListOfCards("name", thisBoard), 0);
        assertEquals(OK, actual.getStatusCode());
    }

    @Test
    public void databaseIsUsed(){
        controller.add(new ListOfCards("name", thisBoard), 0);
        assertTrue(listRepo.calledMethods.contains("save"));
    }
}
