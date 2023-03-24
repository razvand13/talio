package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ServerConnectCtrl {

    private final MainCtrl mainCtrl;

    @FXML
    private TextField port;

    @Inject
    public ServerConnectCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }


    public void connect(){
        String input = port.getText();
        client.utils.ServerUtils.setSERVER("http://localhost:"+input+"/");
        client.utils.ServerUtils.setPort(input);
        mainCtrl.showTaskList();
    }

}
