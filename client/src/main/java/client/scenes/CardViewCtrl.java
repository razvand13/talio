package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class CardViewCtrl implements Initializable {

    private final ServerUtils server;
    private final MainTaskListCtrl mainCtrl;

    @FXML
    private TextField taskEditField;

    @FXML
    private Button saveBtn;

    @FXML
    private Button cancelBtn;

    @FXML
    private Button deleteBtn;

    /**
     * Constructor method
     *
     * @param server   server, which the application uses
     * @param mainCtrl main controller
     */
    @Inject
    public CardViewCtrl(ServerUtils server, MainTaskListCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    /**
     * Method for initialization
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //card title will be fetched from database
        taskEditField.setText("card title");
    }

    /**
     * Method called when 'cancel' button is pressed on the card view scene
     * It forwards the user back to the list overview
     */
    public void cancel() {
        mainCtrl.showTaskListView();
    }

    /**
     * Method called when 'save' butting is pressed on the card view scene
     * It saves the changes to the database, and forwards the user to the list overview
     */
    public void save(){
        //send edit to database
        mainCtrl.showTaskListView();
    }

    /**
     * Method called when 'delete card' butting is pressed on the card view scene
     * It deletes the card from the database, and forwards the user to the list overview
     */
    public void delete(){
        //deletion of card from database
        mainCtrl.showTaskListView();
    }
}