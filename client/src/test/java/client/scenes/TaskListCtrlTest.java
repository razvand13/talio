package client.scenes;

import client.utils.NewServerUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskListCtrlTest {

    private static NewServerUtils server= new NewServerUtils();
    private static MainTaskListCtrl mainCtrl = new MainTaskListCtrl();

    @Test
    void testNotNull(){
        assertNotNull(new TaskListCtrl(server, mainCtrl));
    }
    @Test
    void testEqualsSame(){
        TaskListCtrl taskListCtrl = new TaskListCtrl(server, mainCtrl);
        assertEquals(taskListCtrl, taskListCtrl);
    }


    @Test
    void testNotEqual(){
        TaskListCtrl taskListCtrl = new TaskListCtrl(server, mainCtrl);
        TaskListCtrl taskListCtrl2 = new TaskListCtrl(server, mainCtrl);
        assertNotEquals(taskListCtrl, taskListCtrl2);
    }


}