package client.scenes;

import client.utils.HelperFXinit;
import client.utils.NewServerUtils;

import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminSceneCtrlTest  {

    @Test
    void testConstructor(){
        NewServerUtils server = new NewServerUtils();
        MainTaskListCtrl mainCtrl = new MainTaskListCtrl();
        AdminSceneCtrl adminSceneCtrl = new AdminSceneCtrl(server, mainCtrl);
        assertEquals(adminSceneCtrl.getServer(), server);
        assertEquals(adminSceneCtrl.getMainCtrl(), mainCtrl);
    }

    @Test
    public void testBack() {
        MainTaskListCtrl mainCtrl = mock(MainTaskListCtrl.class);
        AdminSceneCtrl adminCtrl = new AdminSceneCtrl(new NewServerUtils(), mainCtrl);
        adminCtrl.back();
        verify(mainCtrl, times(1)).showOverviewOfBoards();
    }




}