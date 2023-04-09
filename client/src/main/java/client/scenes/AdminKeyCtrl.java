package client.scenes;

import client.utils.OurServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AdminKeyCtrl {

    private final OurServerUtils server;
    private final MainTaskListCtrl mainCtrl;
    private boolean isAdmin;
    @FXML
    private Label inputKeyLabel;
    @FXML
    private TextField keyField;
    @FXML
    private Button okBtn;
    @FXML
    private Label incorrectLabel;

    /**
     * Constructor
     * @param server server
     * @param mainCtrl mainCtrl
     */
    @Inject
    public AdminKeyCtrl(OurServerUtils server, MainTaskListCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Functionality of okBtn
     * Displays admin overview if the key is correct
     * Else displays an error
     */
    public void ok(){
        String inputKey = keyField.getText();
        if(inputKey.equals("password1234!")){ // todo do we want it hardcoded?
            isAdmin = true;
            incorrectLabel.setVisible(false);
            mainCtrl.showAdminOverview();
        }
        else{
            incorrectLabel.setVisible(true);
            keyField.clear();
        }

    }
}
