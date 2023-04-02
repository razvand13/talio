package client.components;

import client.scenes.MainTaskListCtrl;
import client.utils.OurServerUtils;
import commons.Card;
import commons.ListOfCards;
import commons.Quote;
import jakarta.ws.rs.core.GenericType;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;


public class ListContainer extends VBox {
    @FXML
    private Label listNameLabel;
    @FXML
    private ListView<String> list;
    @FXML
    private Button addTaskBtn;
    @FXML
    private TextField taskInputField;
    @FXML
    private Button taskDeleteBtn;
    @FXML
    private Button taskEditBtn;
    @FXML
    private TextField taskEditField;
    @FXML
    private Button listOptionsBtn;
    @FXML
    private Button listDeleteBtn;
    @FXML
    private Button listEditBtn;
    @FXML
    private TextField listRenameField;
    private final OurServerUtils server;

    private final MainTaskListCtrl mainCtrl;

    private ObservableList<Card> data;

    private ListOfCards listOfCards;

    // Since deletion references the list's parent, we need
    // a reference to it inside the container object
    private HBox parent;


    /**
     * Constructor for the custom FXML component
     * An FXMLLoader is needed since we load an external component, from a file,
     * not an already existing one from SceneBuilder
     *
     * @param listName the name of the new List
<<<<<<< HEAD
     *
     * @throws RuntimeException if the FXMLLoader cannot load the component
     * @param server
     * @param mainCtrl
     * @throws RuntimeException if the FXMLLoader cannot load the component
     */
    public ListContainer(String listName, OurServerUtils server, MainTaskListCtrl mainCtrl){
        this.server = server;
        this.mainCtrl = mainCtrl;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/client/components/ListContainer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.setMinWidth(200);
        listNameLabel.setText(listName);
        this.listOfCards = new ListOfCards(listName);

        setHandlers();
    }


    public void setListOfCards(ListOfCards loc){
        listOfCards = loc;
    }

    public void firstTimeSetup1() {
        server.setSession();
        server.registerForMessages("/topic/cards", Card.class, c -> {
            list.getItems().add(c.title);
        });
    }
    /**
     * Method that sets all event handlers of a list and its children
     */
    private void setHandlers(){
        setAddTaskAction(addTaskBtn, taskInputField, list);
        setShowTaskEditAction(taskEditBtn, taskDeleteBtn, list, taskEditField);
        setSaveEditAction(taskEditBtn, taskDeleteBtn, taskEditField, list);
        setDeleteAction(taskDeleteBtn, taskEditBtn, taskEditField, list);
        setListOptions(listNameLabel, listOptionsBtn, listDeleteBtn,
                listEditBtn, listRenameField);
        setRenameList(listNameLabel, listEditBtn, listRenameField, listDeleteBtn);
        setDeleteList(this, listDeleteBtn, listRenameField, listEditBtn);
        setDragAndDrop(list);
    }

    /**
     * Method for adding a new task to a list
     *
     * @param button    'add task' button that's clicked
     * @param textField text field to fetch task title from
     * @param list      list view presenting stored tasks
     */
    public void setAddTaskAction(Button button, TextField textField, ListView<String> list) {
        button.setOnAction(event -> {
            String taskInput = textField.getText();
            if (!taskInput.equals("")) {
//                list.getItems().add(taskInput);

                server.setSession(); // todo is this needed?
                System.out.println("CARD SAVED IN SESSION");
                System.out.println("ListOfCards id = " + listOfCards.id);
                System.out.println(listOfCards);
                Card myCard = new Card(taskInput, "null", "null", listOfCards);
                System.out.println(myCard);
                server.send("/app/cards", myCard);
//                System.out.println(myCard.id);
//                listOfCards.addCard(myCard);

                textField.clear();

//                server.setSession();
//                System.out.println("CARD SAVED IN SESSION");
//                server.send("/app/cards", new Card(taskInput, null, null, null));
//                textField.clear();

//                mainCtrl.showTaskListView();
            }

            event.consume();
        });
    }

    public void firstTimeSetUp(){

    }


    /**
     * Method for making task editing option visible
     *
     * @param editBtn   'edit' button that becomes visible
     * @param delBtn    'delete' button
     * @param list      list view from which an element can be selected
     * @param textField text field to fetch task title from
     */
    public void setShowTaskEditAction(Button editBtn, Button delBtn, ListView<String> list,
                                      TextField textField) {
        list.setOnContextMenuRequested(event -> {
            String item = list.getSelectionModel().getSelectedItem();
            if (item != null) {
                editBtn.setVisible(true);
                delBtn.setVisible(true);
                textField.setVisible(true);
                textField.setText(item);
            } else {
                editBtn.setVisible(false);
                delBtn.setVisible(true);
                textField.setVisible(false);
            }

            event.consume();
        });
    }

    /**
     * Method for saving changes to task title
     *
     * @param editBtn    'edit button' that's clicked
<<<<<<< HEAD
     * @param delBtn    'delete button
     * @param textField text field to fetch new task title from
     * @param list      list view where the change will be presented
     */
    public void setSaveEditAction(Button editBtn, Button delBtn, TextField textField,
                                  ListView<String> list) {
        editBtn.setOnAction(event -> {
            String edit = textField.getText();
            // Check if there is something selected AND if the field is not empty
            int idx = list.getSelectionModel().getSelectedIndex();
            if (idx != -1 && edit.length() >= 1) {
                list.getItems().set(idx, edit);
                editBtn.setVisible(false);
                delBtn.setVisible(false);
                textField.setVisible(false);
            }
        });
    }

    /**
     * Method for deleting a task
     *
     * @param deleteButton  'delete button' that's clicked
     * @param editButton    'edit button' to set invisible
     * @param textField     text field to set invisible
     * @param list          list view where the change will be presented
     */
    public void setDeleteAction(Button deleteButton, Button editButton, TextField textField,
                                ListView<String> list){
        deleteButton.setOnAction(event -> {
            int idx = list.getSelectionModel().getSelectedIndex();
            list.getItems().remove(idx);
            deleteButton.setVisible(false);
            editButton.setVisible(false);
            textField.setVisible(false);
        });
    }

    /**
     * Method for showing / hiding additional (delete, rename) list options
     *
     * @param listNameLabel label to fetch list name from
     * @param clickedButton 'list options' button that's clicked
     * @param deleteButton  delete button that becomes visible
     * @param editButton    edit button that becomes visible
     * @param textField     text field for editing that becomes visible
     */
    public void setListOptions(Label listNameLabel, Button clickedButton, Button deleteButton,
                               Button editButton, TextField textField) {
        clickedButton.setOnAction(event -> {
            boolean visibility = textField.isVisible();
            String listName = listNameLabel.getText();

            textField.setVisible(!visibility);
            textField.setText(listName);
            editButton.setVisible(!visibility);
            deleteButton.setVisible(!visibility);
            event.consume();
        });
    }

    /**
     * Method for deleting the vertical box containing a list
     *
     * @param vBox         vertical box getting deleted
     * @param deleteButton delete button that's clicked
     * @param textField    editing field that needs to be set invisible
     * @param editButton   editing button that needs to be set invisible
     */
    public void setDeleteList(VBox vBox, Button deleteButton,
                              TextField textField, Button editButton) {
        deleteButton.setOnAction(event -> {

            parent.getChildren().remove(vBox);
            deleteButton.setVisible(false);
            textField.setVisible(false);
            deleteButton.setVisible(false);
            editButton.setVisible(false);

            event.consume();
        });

    }

    /**
     * Method for renaming list with given input
     *
     * @param listNameLabel label presenting the name of the list
     * @param editButton    edit button that is clicked
     * @param textField     text field to fetch input from
     * @param deleteButton  delete button that needs to be set invisible
     */
    public void setRenameList(Label listNameLabel, Button editButton,
                              TextField textField, Button deleteButton) {
        editButton.setOnAction(event -> {
            String newName = textField.getText();
            if (!newName.equals("")) {
                listNameLabel.setText(newName);
                editButton.setVisible(false);
                textField.setVisible(false);
                deleteButton.setVisible(false);
            }
            event.consume();
        });

    }


    /**
     * Sets drag and drop event handlers on a ListView
     * Lists are both sources and targets of this operation,
     * so all handlers will be applied on them directly
     * These handlers only work with Strings as the content of lists
     * @param list list to apply handlers to
     */
    public void setDragAndDrop(ListView<String> list) {
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
     * @param list  source list
     * @param event clicking on a list item
     */
    private void dragDetected(ListView<String> list, MouseEvent event) {
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
     * @param list  target list
     * @param event dragging data over another list
     */
    private void dragOver(ListView<String> list, DragEvent event) {
        // Only execute if there is data that is being dragged
        if (event.getDragboard().hasString()) {
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
     * @param list  target list
     * @param event dropping dragged data
     */
    private void dragDropped(ListView<String> list, DragEvent event) {
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasString()) {
            String dbContent = db.getString();
            list.getItems().add(dbContent);
            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
    }

    /**
     * Adds a dragDone event handler on a list
     * Once the drag is over, since the dragDropped(ListView<String>, DragEvent)
     * method offers the new data to that list,
     * the same data from the old list must be removed
     *
     * @param list  the list the data was dragged from
     * @param event the drag operation has finished
     */
    private void dragDone(ListView<String> list, DragEvent event) {
        String selectedItem = list.getSelectionModel().getSelectedItem();

        if (event.getTransferMode() == TransferMode.MOVE) {
            list.getItems().remove(selectedItem);
        }

        event.consume();
    }

    /**
     * Adds a dragEntered event handler on a list
     * When the user is in the process of dragging an item and hovers over a list,
     * that list gets a border shadow
     *
     * @param list  target list
     * @param event entering the premise of a list while dragging an item
     */
    private void dragEntered(ListView<String> list, DragEvent event) {
        if (event.getDragboard().hasString()) {
            list.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0)");
        }

        event.consume();
    }

    /**
     * Adds a dragEntered event handler on a list
     * When the user is in the process of dragging an item and is no longer
     * hovering of a list, that list's border shadow is removed
     *
     * @param list  target list
     * @param event exiting the premise of a list while dragging an item
     */
    private void dragExited(ListView<String> list, DragEvent event) {
        list.setStyle("");
        event.consume();
    }

//    public void refreshList(List<Card> data){
//        var currentCards = list.getItems();
//        for(String card : currentCards){
//            currentCards.remove(card);
//        }
//
//        for(Card card : data){
//            if(card.list.id == listOfCards.id){
//                list.getItems().add(card.title);
//            }
//        }
//    }

    /**
     * Setter method for parent
     * @param parent parent
     */
    public void setParent(HBox parent) {
        this.parent = parent;
    }

    /**
     * Getter method for listNameLabel
     * @return listNameLabel
     */
    public Label getListNameLabel() {
        return listNameLabel;
    }

    /**
     * Getter method for list
     * @return list
     */
    public ListView<String> getList() {
        return list;
    }

    /**
     * Getter method for addTaskBtn
     * @return addTaskBtn
     */
    public Button getAddTaskBtn() {
        return addTaskBtn;
    }

    /**
     * Getter method for taskInputField
     * @return taskInputField
     */
    public TextField getTaskInputField() {
        return taskInputField;
    }

    /**
     * Getter method for taskEditBtn
     * @return taskEditBtn
     */
    public Button getTaskEditBtn() {
        return taskEditBtn;
    }

    /**
     * Getter method for taskEditField
     * @return taskEditField
     */
    public TextField getTaskEditField() {
        return taskEditField;
    }

    /**
     * Getter method for listOptionsBtn
     * @return listOptionsBtn
     */
    public Button getListOptionsBtn() {
        return listOptionsBtn;
    }

    /**
     * Getter method for listDeleteBtn
     * @return listDeleteBtn
     */
    public Button getListDeleteBtn() {
        return listDeleteBtn;
    }

    /**
     * Getter method for listEditBtn
     * @return listEditBtn
     */
    public Button getListEditBtn() {
        return listEditBtn;
    }

    /**
     * Getter method for listRenameField
     * @return listRenameField
     */
    public TextField getListRenameField() {
        return listRenameField;
    }

    /**
     * Getter for listOfCards
     * @return the listOfCards associated with this container
     */
    public ListOfCards getListOfCards() {
        return listOfCards;
    }

//    /**
//     * Adds one card to the ListView and displays it
//     * @param card card to be added
//     */
//    public void addCard(Card card){
////        var names = cards.stream().map(c -> c.title).toList();
//        String cardTitle = card.title;
//        list.getItems().add(cardTitle);
//    }
}
