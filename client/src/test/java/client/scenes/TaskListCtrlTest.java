package client.scenes;

import client.utils.HelperFXinit;
import client.utils.OurServerUtils;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskListCtrlTest  {

    public static MainTaskListCtrl mainCtrl = new MainTaskListCtrl();
    public static OurServerUtils server = new OurServerUtils();
    public static TaskListCtrl taskListCtrl = new TaskListCtrl(server, mainCtrl);

    @Test
    public void testConstructor(){
        assertEquals(taskListCtrl.getServer(), server);
        assertEquals(taskListCtrl.getMainCtrl(), mainCtrl);
    }

    @Test
    void testNotNull(){
        assertNotNull(new TaskListCtrl(server, mainCtrl));
    }
    @Test
    void testEqualsSame(){
        assertEquals(taskListCtrl, taskListCtrl);
    }


    @Test
    void testNotEqual(){
        TaskListCtrl taskListCtrl2 = new TaskListCtrl(server, mainCtrl);
        assertNotEquals(taskListCtrl, taskListCtrl2);
    }


}