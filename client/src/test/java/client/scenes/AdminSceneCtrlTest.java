package client.scenes;

import client.utils.HelperFXinit;
import client.utils.OurServerUtils;

import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdminSceneCtrlTest extends HelperFXinit {

    @Test
    void testConstructor(){
        OurServerUtils server = new OurServerUtils();
        MainTaskListCtrl mainCtrl = new MainTaskListCtrl();
        AdminSceneCtrl adminSceneCtrl = new AdminSceneCtrl(server, mainCtrl);
        assertEquals(adminSceneCtrl.getServer(), server);
        assertEquals(adminSceneCtrl.getMainCtrl(), mainCtrl);
    }

    @Test
    public void testBack() {
        MainTaskListCtrl mainCtrl = mock(MainTaskListCtrl.class);
        AdminSceneCtrl adminCtrl = new AdminSceneCtrl(new OurServerUtils(), mainCtrl);
        adminCtrl.back();
        verify(mainCtrl, times(1)).showOverviewOfBoards();
    }




}