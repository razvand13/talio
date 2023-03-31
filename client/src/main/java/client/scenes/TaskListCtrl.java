package client.scenes;

import client.components.ListContainer;
import client.utils.OurServerUtils;
import com.google.inject.Inject;
//import commons.Card;
import commons.Card;
import commons.ListOfCards;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskListCtrl implements Initializable {

    private final OurServerUtils server;
    private final MainTaskListCtrl mainCtrl;

    private ObservableList<Card> data;

    @FXML
    private HBox hBox;

    @FXML
    private TextField listTitle;

    /**
     * Constructor method
     *
     * @param server   server, which the application uses
     * @param mainCtrl main controller
     */
    @Inject
    public TaskListCtrl(OurServerUtils server, MainTaskListCtrl mainCtrl) {
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
     * Creates a custom ListContainer FXML component with proper functionality
     */
    public void addNewList() {

        // Don't allow empty names, use default
        String listName = listTitle.getText();
        if (listName.equals("")) listName = "ToDo";

        ListContainer container = new ListContainer(listName, server, mainCtrl);

        // Reset text
        listTitle.setText("ToDo");

        container.setParent(hBox);
        hBox.getChildren().add(container);

        ListOfCards mylist = new ListOfCards(listName);
        server.send("/app/lists", mylist);

    }

    public void firstTimeSetUp(){
        server.setSession();
        System.out.println("NEW TASK LIST");
        server.registerForMessages("/topic/cards", Card.class, c -> {
            data.add(c);
            System.out.println("NEW TASK LIST");
        });
    }



}
