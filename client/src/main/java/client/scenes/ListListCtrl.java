package client.scenes;

import DataStructures.Card;
import DataStructures.ListOfCards;
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
import java.util.ArrayList;
import java.util.List;
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

    @Inject
    public ListListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
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
    public void addNew(){
        String input = textInput.getText();
        if(input != null || input.length() < 1) {
            List<Card> listOfTasks = new ArrayList<>();
            listList.add(new ListOfCards(textInput.getText(), null));
            list.add(input);
        }
        textInput.clear();
        refresh();
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




}
