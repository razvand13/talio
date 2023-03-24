package client.scenes;

import commons.ListOfCards;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import java.net.URL;

import java.util.ResourceBundle;

public class ListListCtrl implements Initializable{

    private final ServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private TextField editTextInput;
    @FXML
    private TextField textInput;
    @FXML
    private ListView<String> myListView;
    @FXML
    private Button editButton;

    ObservableList<String> list = FXCollections.observableArrayList();

    ObservableList<ListOfCards> listList = FXCollections.observableArrayList();

    /**Constructor for ListListCtrl class
     *
     * @param server - the server to put it on
     * @param mainCtrl - the main controller
     */
    @Inject
    public ListListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**Method for making editButton and editTextInput hidden
     *
     * NOT USED ANYWHERE
     *
     * @param location
     * The location used to resolve relative paths for the root object, or
     * {@code null} if the location is not known.
     *
     * @param resources
     * The resources used to localize the root object, or {@code null} if
     * the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //editing should only be available when right-clicked on a task
        editButton.setVisible(false);
        editTextInput.setVisible(false);
    }

    /**Refresh method for ListListCtrl
     *
     */
    public void refresh() {
        myListView.setItems(list);
    }

    /** Adding a new List to the Board
     *
     */
    public void addNew(){
        String input = textInput.getText();
        if(input != null || input.length() < 1) {
            listList.add(new ListOfCards(textInput.getText(), null));
            list.add(input);
        }
        textInput.clear();
        refresh();
    }

    /**Remove a List from the Board
     *
     */
    public void removeList(){
        String input = textInput.getText();
        if(input != null){
            listList.remove(input);
            list.remove(input);
        }
        textInput.clear();
        refresh();
    }




}
