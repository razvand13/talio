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

    /**
     * Constructor for ServerConnectCtrl
     *
     * @param mainCtrl
     */
    @Inject
    public ServerConnectCtrl(MainTaskListCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
    }

    /**
     * Method to set the server to the port
     */
    public void connect(){
        String portInput = port.getText();
        if(portInput.equals("")) portInput = "8080";

        String addressInput = address.getText();
        if(addressInput.equals("")) addressInput = "localhost";

        client.utils.OurServerUtils.setSERVER("http://"+ addressInput+ ":" +portInput+"/");
//        client.utils.OurServerUtils.setPort(portInput);
        mainCtrl.showOverviewOfBoards();
    }

    /** Getter for main controller
     * @return main controller
     */
    public MainTaskListCtrl getMainCtrl() {
        return mainCtrl;
    }

}
