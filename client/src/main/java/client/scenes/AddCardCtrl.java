package client.scenes;

import DataStructures.Card;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AddCardCtrl {

    private final ServerUtils server;
    private final MainTaskListCtrl mainCtrl;

    @FXML
    private TextField cardTitle;

    /**
     * Constructor method
     *
     * @param server   server, which the application uses
     * @param mainCtrl main controller
     */
    @Inject
    public AddCardCtrl(ServerUtils server, MainTaskListCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    /**
     * Method called when 'cancel' button is pressed on the add card scene
     * It forwards the user back to the list overview
     */
    public void cancel() {
        clearFields();
        mainCtrl.showTaskListView();
    }

    /**
     * Method called when 'ok' button is pressed on the add card scene
     * It sends the created card to the server,
     * then forwards the user back to list overview
     */
    public void ok() {
        //the destination might need to be changed
        server.send("/app/cards", getCard());
        clearFields();
        mainCtrl.showTaskListView();
    }

    /**
     * Method for creating a new card with title input as a String
     *
     * @return a new Card object
     */
    private Card getCard() {
        var title = cardTitle.getText();
        return new Card(title, null, null, null);
    }

    /**
     * Method that clears the text input field
     */
    private void clearFields() {
        cardTitle.clear();
    }
}

