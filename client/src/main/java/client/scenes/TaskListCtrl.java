package client.scenes;

import DataStructures.Card;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskListCtrl implements Initializable{


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

        //list containing String presented in the list view
        ObservableList<String> list = FXCollections.observableArrayList();

        //list of cards created by given title
        ObservableList<Card> cardList = FXCollections.observableArrayList();

    /**Constructor method
     *
     * @param server server, which the application uses
     * @param mainCtrl main controller
     */
    @Inject
    public TaskListCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }


    /**Method for initialization
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

    /**Method for presenting data in the listview
     */
    public void refresh() {
        myListView.setItems(list);
    }


    /**Method for adding new card to the list, with title from text field input
     */
    public void addNew(){
        String input = textInput.getText();
        if(input != null) {
            cardList.add(new Card(textInput.getText(), null, null, null, null));
            list.add(input);
        }
        textInput.clear();
        refresh();
    }

    /**Method for removing a card from the list
     *
     */
    public void removeCard(){
        String input = textInput.getText();
        if(input != null){
            cardList.remove(input);
            list.remove(input);
        }
        textInput.clear();
        refresh();
    }

    /**Method called when context menu requested on the view list
     * If an existing item is selected, the editing option becomes visible
     */
    public void editItem(){
            String item = myListView.getSelectionModel().getSelectedItem();
            if(item != null){
                editButton.setVisible(true);
                editTextInput.setVisible(true);
                editTextInput.setText(item);
            }
            else{
                editButton.setVisible(false);
                editTextInput.setVisible(false);
            }
        }

    /**Method called when clicking off of selected item while in the editing process
     * It makes the editing option invisible again
     * @param mouseEvent mouse click on view list
     */
    public void finishEditing(MouseEvent mouseEvent) {
            editButton.setVisible(false);
            editTextInput.setVisible(false);
        }

    /**Method that modifies the content of the list to the input of the text field.
     * After saving the changes, the edition option becomes invisible again
     *
     * @param actionEvent - method triggered by pressing 'Save' button in TaskListView
     */
    public void saveEdit(ActionEvent actionEvent) {
        String edit = editTextInput.getText();
        if(edit.length() >= 1) {
            list.set(myListView.getSelectionModel().getSelectedIndex(), edit);
            cardList.get(Math.max((myListView.getSelectionModel().getSelectedIndex()),
                    0)).title = editTextInput.getText();
            editButton.setVisible(false);
            editTextInput.setVisible(false);
            refresh();
        }
    }


}
