package client.scenes;

import client.components.ListContainer;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskListCtrl implements Initializable {

    private final ServerUtils server;
    private final MainTaskListCtrl mainCtrl;

    @FXML
    private HBox hBox;

    /**
     * Constructor method
     *
     * @param server   server, which the application uses
     * @param mainCtrl main controller
     */
    @Inject
    public TaskListCtrl(ServerUtils server, MainTaskListCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
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
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * Method called when 'add list' button is clicked
     * Creates a new vertical box and fills it with objects necessary
     * for the functionality of a task list. Event related methods get assigned.
     */
    public void addNewList() {
        ListContainer container = new ListContainer();
        container.setParent(hBox);
        hBox.getChildren().add(container);
    }

    public Card getCard() {
        return new Card(taskInput, "", "", null);
    }

    public void firstTimeSetUp(){
        server.setSession();
        server.registerForMessages("/topic/cards", Card.class, c -> {
            list.getItems.add(c);
        });
    }


}
