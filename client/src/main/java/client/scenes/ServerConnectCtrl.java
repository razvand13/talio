package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ServerConnectCtrl {

    private final MainTaskListCtrl mainCtrl;

    @FXML
    private TextField port;
    @FXML
    private TextField address;

    @Inject
    public ServerConnectCtrl(MainTaskListCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }


    public void connect(){
        String portInput = port.getText();
        String addressInput = address.getText();
        client.utils.OurServerUtils.setSERVER("http://"+ addressInput+ ":" +portInput+"/");
        client.utils.OurServerUtils.setPort(portInput);
        mainCtrl.showTaskListView();
    }

}
