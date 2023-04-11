package client.scenes;

import client.utils.HelperFXinit;
import client.utils.OurServerUtils;

import commons.Board;
import org.junit.jupiter.api.Test;

//import class called Board from commons package


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OverviewOfBoardsCtrlTest  {

    @Test
    public void testConstructor(){
        OurServerUtils serverUtils = new OurServerUtils();
        MainTaskListCtrl mainCtrl = new MainTaskListCtrl();
        TaskListCtrl taskListCtrl = new TaskListCtrl(serverUtils, mainCtrl);
        AdminSceneCtrl adminSceneCtrl = new AdminSceneCtrl(serverUtils, mainCtrl);
        OverviewOfBoardsCtrl overviewOfBoardsCtrl = new OverviewOfBoardsCtrl(serverUtils, mainCtrl,
                taskListCtrl, adminSceneCtrl);
        assertEquals(overviewOfBoardsCtrl.getMainCtrl(), mainCtrl);
        assertEquals(overviewOfBoardsCtrl.getServer(), serverUtils);
        assertEquals(overviewOfBoardsCtrl.getTaskListCtrl(), taskListCtrl);
        assertEquals(overviewOfBoardsCtrl.getAdminSceneCtrl(), adminSceneCtrl);
    }










}