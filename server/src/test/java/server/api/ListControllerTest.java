package server.api;

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
    TestListRepository listRepo;
    TestBoardRepository boardRepo;
    ListController controller;

    @BeforeEach
    public void setup() {
        listRepo = new TestListRepository();
        boardRepo = new TestBoardRepository();
        controller = new ListController(listRepo, boardRepo);
    }

    @Test
    public void cannotAddNullList() {
        var actual = controller.add(null);
        assertEquals(BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void canAddGoodList(){
        var actual = controller.add(new ListOfCards("name"));
        assertEquals(OK, actual.getStatusCode());
    }

    @Test
    public void databaseIsUsed(){
        controller.add(new ListOfCards("name"));
        assertTrue(listRepo.calledMethods.contains("save"));
    }

    @Test
    public void getAllTest() {
        ListOfCards list0 = new ListOfCards("name");
        ListOfCards list1 = new ListOfCards("name");
        list0.id = 0;
        list1.id = 1;
        controller.add(list0);
        controller.add(list1);

        List expected = new ArrayList<ListOfCards>(Arrays.asList(list0, list1));

        assertEquals(expected, controller.getAll());
    }

    @Test
    public void getByIdTest() {
        ListOfCards list0 = new ListOfCards("name");
        list0.id = 0;
        controller.add(list0);

        assertEquals(list0, controller.getById(0).getBody());
    }

    @Test
    public void deleteByIdTest() {
        ListOfCards list0 = new ListOfCards("name");
        ListOfCards list1 = new ListOfCards("name");
        list0.id = 0;
        list1.id = 1;
        controller.add(list0);
        controller.add(list1);

        controller.deleteById(1);

        assertEquals(BAD_REQUEST, controller.getById(1).getStatusCode());
        assertEquals(OK, controller.getById(0).getStatusCode());
    }

    @Test
    public void deleteAllTest() {
        ListOfCards list0 = new ListOfCards("name");
        ListOfCards list1 = new ListOfCards("name");
        list0.id = 0;
        list1.id = 1;
        controller.add(list0);
        controller.add(list1);

        controller.deleteAll();

        assertEquals(BAD_REQUEST, controller.getById(1).getStatusCode());
        assertEquals(BAD_REQUEST, controller.getById(0).getStatusCode());
    }
    @Test
    public void addMessageTest(){
        ListOfCards list = new ListOfCards("name");
        list.id = 0;
        controller.addMessage(list);
        assertEquals(OK, controller.getById(0).getStatusCode());
        assertTrue(listRepo.calledMethods.contains("save"));
    }
}
