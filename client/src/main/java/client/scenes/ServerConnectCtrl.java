package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ServerConnectCtrl {

    private final MainTaskListCtrl mainCtrl;

    @FXML
    private TextField port;

    @Inject
    public ServerConnectCtrl(MainTaskListCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }


    public void connect(){
        String input = port.getText();
        client.utils.OurServerUtils.setSERVER("http://localhost:"+input+"/");
        client.utils.OurServerUtils.setPort(input);
        mainCtrl.showTaskListView();
    }

}
