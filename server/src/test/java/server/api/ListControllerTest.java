package server.api;

import commons.Board;
import commons.ListOfCards;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void getAllTest() {
        Board myBoard = new Board("name", "colour");
        ListOfCards list0 = new ListOfCards("name", myBoard);
        ListOfCards list1 = new ListOfCards("name", myBoard);
        list0.id = 0;
        list1.id = 1;
        controller.add(list0, myBoard.id);
        controller.add(list1, myBoard.id);

        List expected = new ArrayList<ListOfCards>(Arrays.asList(list0, list1));

        assertEquals(expected, controller.getAll());
    }

    @Test
    public void getByIdTest() {
        Board myBoard = new Board("name", "colour");
        ListOfCards list0 = new ListOfCards("name", myBoard);
        list0.id = 0;
        controller.add(list0, myBoard.id);

        assertEquals(list0, controller.getById(0).getBody());
    }

    @Test
    public void deleteByIdTest() {
        Board myBoard = new Board("name", "colour");
        ListOfCards list0 = new ListOfCards("name", myBoard);
        ListOfCards list1 = new ListOfCards("name", myBoard);
        list0.id = 0;
        list1.id = 1;
        controller.add(list0, myBoard.id);
        controller.add(list1, myBoard.id);

        controller.deleteById(1);

        assertEquals(BAD_REQUEST, controller.getById(1).getStatusCode());
        assertEquals(OK, controller.getById(0).getStatusCode());
    }

    @Test
    public void deleteAllTest() {
        Board myBoard = new Board("name", "colour");
        ListOfCards list0 = new ListOfCards("name", myBoard);
        ListOfCards list1 = new ListOfCards("name", myBoard);
        list0.id = 0;
        list1.id = 1;
        controller.add(list0, myBoard.id);
        controller.add(list1, myBoard.id);

        controller.deleteAll();

        assertEquals(BAD_REQUEST, controller.getById(1).getStatusCode());
        assertEquals(BAD_REQUEST, controller.getById(0).getStatusCode());
    }
}
