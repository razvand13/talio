package client.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ServerConnectCtrl {

    private final MainCtrl mainCtrl;

    @FXML
    private TextField port;
    @FXML
    private TextField address;

    /**Constructor for ServerConnectCtrl
     *
     * @param mainCtrl
     */
    @Inject
    public ServerConnectCtrl(MainCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**Method to set the server to the port
     *
     */
    public void connect(){
        String portInput = port.getText();
        String addressInput = address.getText();
        client.utils.ServerUtils.setSERVER("http://"+ addressInput+ ":" +portInput+"/");
        client.utils.ServerUtils.setPort(portInput);
        mainCtrl.showOverview();
    }

}
