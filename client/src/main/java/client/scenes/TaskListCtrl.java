package client.scenes;

import client.components.ListContainer;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import client.utils.OurServerUtils;
import com.google.inject.Inject;
//import commons.Card;
import commons.Card;
import commons.ListOfCards;
import jakarta.ws.rs.core.GenericType;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import javax.swing.*;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TaskListCtrl implements Initializable {

    private final OurServerUtils server;
    private final MainTaskListCtrl mainCtrl;

//    private ObservableList<Card> data;
    private List<Card> data;
    private List<ListOfCards> list;

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
        server.send("/app/lists", container.getListOfCards());
    }

    public void firstTimeSetUp() {
        server.setSession();
        System.out.println("NEW TASK LIST");

        refreshBoard();
        list = server.getLists();
        data = server.getCards();

        server.registerForMessages("/topic/cards", Card.class, c -> {
            data.add(c);
            Platform.runLater(this::refreshBoard);
        });

        server.registerForMessages("/topic/lists", ListOfCards.class, l -> {
            list.add(l);
            Platform.runLater(this::refreshBoard);
        });

//        for(ListOfCards l: list) {
//            ListContainer container = new ListContainer(l.name, server, mainCtrl);
//            container.setListOfCards(l);
//            container.setParent(hBox);
//            hBox.getChildren().add(container);
//        }
    }

    /**
     * Method that refreshes the board
     * First removes all lists and their contents, and using the data and lists from the server,
     * redraws them, one by one (not the best approach, but it should work)
     * todo maybe come up with a better idea
     */
    public void refreshBoard(){
        Platform.runLater(() ->{
            clearBoard();
            makeBoard();
        });
    }

    /**
     * Erase all lists from the board
     */
    public void clearBoard(){
        for(Node child : hBox.getChildren()){
            if(child.getClass() == ListContainer.class){
                ListContainer listContainer = (ListContainer) child;
                hBox.getChildren().remove(listContainer);
            }
        }
    }

    /**
     * Make the board using 'data' and 'list'
     */
    public void makeBoard(){
        //Redraw lists
        for(ListOfCards loc : list){
            ListContainer listContainer = new ListContainer(loc.name, server, mainCtrl);
            listContainer.setListOfCards(loc);
            listContainer.setParent(hBox);
            hBox.getChildren().add(listContainer);
        }

        //Redraw list contents
        for(Node child : hBox.getChildren()){
            if(child.getClass() == ListContainer.class){ // Error handling
                ListContainer listContainer = (ListContainer) child;
                var currentCards = listContainer.getList().getItems();

                // Remove all contents of each list
                for(String card : currentCards){
                    currentCards.remove(card);
                }

                ListOfCards listOfCards = listContainer.getListOfCards();

                // Add back each card to their own list
                for(Card card : data){
                    if(card.list.id == listOfCards.id){
                        var items = listContainer.getList().getItems();
                        items.add(card.title);
                    }
                }
            }
        }
    }

}
