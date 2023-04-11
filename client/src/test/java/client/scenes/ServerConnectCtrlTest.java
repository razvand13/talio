package client.scenes;

import client.utils.HelperFXinit;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerConnectCtrlTest {

    @Test
    public void testConstructor(){
        MainTaskListCtrl mainCtrl = new MainTaskListCtrl();
        ServerConnectCtrl serverConnectCtrl = new ServerConnectCtrl(mainCtrl);
        assertEquals(serverConnectCtrl.getMainCtrl(), mainCtrl);
    }

}