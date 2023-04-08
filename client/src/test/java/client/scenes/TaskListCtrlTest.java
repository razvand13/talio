package client.scenes;

import client.utils.OurServerUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskListCtrlTest {

    private static OurServerUtils server= new OurServerUtils();
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


    @Test
    void testToString() {
        TaskListCtrl taskListCtrl = new TaskListCtrl(server, mainCtrl);
        assertEquals(taskListCtrl.toString(), taskListCtrl.toString());
    }

}