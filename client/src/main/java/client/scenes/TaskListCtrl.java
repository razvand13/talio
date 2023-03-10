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
import javafx.scene.input.*;

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

        // Will add a temporary hard-coded list just for the sake of testing drag and drop functionality
        @FXML
        private ListView<String> tempListView;

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

        // Add drag and drop event handlers to lists
        setDragAndDrop(myListView);
        setDragAndDrop(tempListView);
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

    /**
     * Sets drag and drop event handlers on a ListView
     * Lists are both sources and targets of this operation, so all handlers will be applied on them directly
     * These handlers only work with Strings as the content of lists
     *
     * @param list list to apply handlers to
     */
    public void setDragAndDrop(ListView<String> list){
        // Functionality
        list.setOnDragDetected(event -> dragDetected(list, event));
        list.setOnDragOver(event -> dragOver(list, event));
        list.setOnDragDropped(event -> dragDropped(list, event));
        list.setOnDragDone(event -> dragDone(list, event));

        // Styling
        list.setOnDragEntered(event -> dragEntered(list, event));
        list.setOnDragExited(event -> dragExited(list, event));
    }

    /**
     * Adds a dragDetected event handler on a list
     * When a drag is detected, add the contents of the item dragged into the dragboard
     *
     * @param list source list
     * @param event clicking on a list item
     */
    private void dragDetected(ListView<String> list, MouseEvent event){
        String selectedItem = list.getSelectionModel().getSelectedItem();

        Dragboard db = list.startDragAndDrop(TransferMode.MOVE);

        ClipboardContent content = new ClipboardContent();
        content.putString(selectedItem);
        db.setContent(content);

        event.consume();
    }

    /**
     * Adds a dragOver event handler on a list
     * When a drag is in progress and the users drags content over another list,
     * be ready to accept that data
     *
     * @param list target list
     * @param event dragging data over another list
     */
    private void dragOver(ListView<String> list, DragEvent event){
        // Only execute if there is data that is being dragged
        if(event.getDragboard().hasString()){
            event.acceptTransferModes(TransferMode.MOVE);
        }

        event.consume();
    }

    /**
     * Adds a dragDropped event handler on a list
     * The success variable is needed if the user drops the data somewhere other than another list.
     * This way the data isn't lost and the drop is not considered completed,
     * preserving the ACID-ity of the operation.
     *
     * @param list target list
     * @param event dropping dragged data
     */
    private void dragDropped(ListView<String> list, DragEvent event){
        Dragboard db = event.getDragboard();
        boolean success = false;

        if(db.hasString()){
            String dbContent = db.getString();
            list.getItems().add(dbContent);
            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
    }

    /**
     * Adds a dragDone event handler on a list
     * Once the drag is over, since the dragDropped(ListView<String>, DragEvent) method offers the new data to that list,
     * the same data from the old list must be removed
     *
     * @param list the list the data was dragged from
     * @param event the drag operation has finished
     */
    private void dragDone(ListView<String> list, DragEvent event){
        String selectedItem = list.getSelectionModel().getSelectedItem();

        if(event.getTransferMode() == TransferMode.MOVE){
            list.getItems().remove(selectedItem);
        }

        event.consume();
    }

    /**
     * Adds a dragEntered event handler on a list
     * When the user is in the process of dragging an item and hovers over a list,
     * that list gets a border shadow
     *
     * @param list target list
     * @param event entering the premise of a list while dragging an item
     */
    private void dragEntered(ListView<String> list, DragEvent event){
        if(event.getDragboard().hasString()){
            list.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0)");
        }

        event.consume();
    }

    /**
     * Adds a dragEntered event handler on a list
     * When the user is in the process of dragging an item and is no longer
     * hovering of a list, that list's border shadow is removed
     *
     * @param list target list
     * @param event exiting the premise of a list while dragging an item
     */
    private void dragExited(ListView<String> list, DragEvent event){
        list.setStyle("");
        event.consume();
    }

}
