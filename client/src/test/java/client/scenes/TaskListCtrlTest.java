package client.scenes;

import client.utils.HelperFXinit;
import client.utils.OurServerUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskListCtrlTest extends HelperFXinit {

    @Test
    public void testConstructor(){
        MainTaskListCtrl mainCtrl = new MainTaskListCtrl();
        OurServerUtils server= new OurServerUtils();
        TaskListCtrl taskListCtrl = new TaskListCtrl(server, mainCtrl);
        assertEquals(taskListCtrl.getServer(), server);
        assertEquals(taskListCtrl.getMainCtrl(), mainCtrl);
    }

    @Test
    void testNotNull(){
        OurServerUtils server= new OurServerUtils();
        MainTaskListCtrl mainCtrl = new MainTaskListCtrl();
        assertNotNull(new TaskListCtrl(server, mainCtrl));
    }
    @Test
    void testEqualsSame(){
        OurServerUtils server= new OurServerUtils();
        MainTaskListCtrl mainCtrl = new MainTaskListCtrl();
        TaskListCtrl taskListCtrl = new TaskListCtrl(server, mainCtrl);
        assertEquals(taskListCtrl, taskListCtrl);
    }


    @Test
    void testNotEqual(){
        MainTaskListCtrl mainCtrl = new MainTaskListCtrl();
        OurServerUtils server= new OurServerUtils();
        TaskListCtrl taskListCtrl = new TaskListCtrl(server, mainCtrl);
        TaskListCtrl taskListCtrl2 = new TaskListCtrl(server, mainCtrl);
        assertNotEquals(taskListCtrl, taskListCtrl2);
    }


}