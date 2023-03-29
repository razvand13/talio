package client.scenes;

import client.Main;
import client.utils.OurServerUtils;
import commons.Board;
import commons.ListOfCards;
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

    private final OurServerUtils server;
    private final MainCtrl mainCtrl;
    @FXML
    private TextField editTextInput;
    @FXML
    private TextField textInput;
    @FXML
    private ListView<String> myListView;
    @FXML
    private Button editButton;

    private Board board;

    ObservableList<String> list = FXCollections.observableArrayList();

    ObservableList<ListOfCards> listList = FXCollections.observableArrayList();

    @Inject
    public ListListCtrl(OurServerUtils server, MainCtrl mainCtrl, Board board) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.board = board;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //editing should only be available when right-clicked on a task
        editButton.setVisible(false);
        editTextInput.setVisible(false);
    }

    public void refresh() {
        myListView.setItems(list);
    }

    /** Adding a new List to the List of Lists
     *
     */
    public void addNewList(){
        server.send("/app/boards", getList());
        mainCtrl.showOverview0();
    }

    private ListOfCards getList() {
        String name = "null";
        return new ListOfCards(name, board);
    }

    public void removeList(){
        String input = textInput.getText();
        if(input != null){
            listList.remove(input);
            list.remove(input);
        }
        textInput.clear();
        refresh();
    }

    public void firstTimeSetUp(){
        server.setSession();
        server.registerForMessages("/topic/boards", ListOfCards.class, loc -> {
            listList.add(loc);
        });
    }


}
