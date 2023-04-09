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
     *
     * @return server address string
     */
    public String connect(){
        String portInput = port.getText();
        if(portInput.equals("")) portInput = "8080";

        String addressInput = address.getText();
        if(addressInput.equals("")) addressInput = "localhost";

        String serverAddress = "http://"+ addressInput+ ":" +portInput+"/";

        client.utils.OurServerUtils.setSERVER(serverAddress);
        mainCtrl.showTaskListView();

        return serverAddress;
    }

    /**
     * Setter method for the port field
     * Used for testing
     * @param port port
     */
    public void setPort(TextField port) {
        this.port = port;
    }

    /**
     * Setter method for the address field
     * Used for testing
     * @param address addres
     */
    public void setAddress(TextField address) {
        this.address = address;
    }
}
