package client.scenes;

import javafx.scene.control.TextField;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServerConnectCtrlTest {

    private ServerConnectCtrl serverConnectCtrl;
    private TextField portField;
    private TextField addressField;

    @BeforeEach
    void setup(){
        serverConnectCtrl = new ServerConnectCtrl(new MainTaskListCtrl());
        portField = new TextField();
        addressField = new TextField();
    }

    @Test
    void testConnectEmpty() {
        portField.setText("");
        addressField.setText("");
        serverConnectCtrl.setPort(portField);
        serverConnectCtrl.setAddress(addressField);
        assertEquals("http://localhost:8080/", serverConnectCtrl.connect());
    }

    @Test
    void testConnectInput(){
        portField.setText("9090");
        addressField.setText("127.0.0.1");
        serverConnectCtrl.setPort(portField);
        serverConnectCtrl.setAddress(addressField);
        assertEquals("http://127.0.0.1:9090/", serverConnectCtrl.connect());
    }
}