package client.components;

import javafx.collections.FXCollections;
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
import java.util.Arrays;

public class ListContainer extends VBox {
    @FXML
    private Label listNameLabel;
    @FXML
    private ListView<String> list;
    private ObservableList<String> obList;
    @FXML
    private Button addTaskBtn;
    @FXML
    private TextField taskInputField;
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

    // Since deletion references the list's parent, we need a reference to it inside the container object
    private HBox parent;


    /**
     * Constructor for the custom FXML component
     * An FXMLLoader is needed since we load an external component, from a file,
     * not an already existing one from SceneBuilder
     *
     * @throws RuntimeException if the FXMLLoader cannot load the component
     */
    public ListContainer(){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/client/components/ListContainer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.setMinWidth(200);

        obList = FXCollections.observableArrayList();
        setHandlers();

        // Had a few issues with initialization, this is how I finally got it to work
        // Maybe it would be enough to use obList, not sure
        var children = FXCollections.observableArrayList();
        var childNodes = Arrays.asList(listNameLabel, list, addTaskBtn, taskInputField, taskEditBtn,
                        taskEditField, listOptionsBtn, listDeleteBtn, listEditBtn, listRenameField);
        children.addAll(childNodes);

    }

    /**
     * Method that sets all event handlers of a list and its children
     */
    private void setHandlers(){
        setAddTaskAction(addTaskBtn, taskInputField, obList, list);
        setShowTaskEditAction(taskEditBtn, list, taskEditField);
        setSaveEditAction(taskEditBtn, taskEditField, obList, list);
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
     * @param obList    observable list storing tasks
     * @param list      list view presenting stored tasks
     */
    public void setAddTaskAction(Button button, TextField textField,
                                 ObservableList<String> obList, ListView<String> list) {
        button.setOnAction(event -> {
            String taskInput = textField.getText();
            if (!taskInput.equals("")) {
                obList.add(textField.getText());
                list.setItems(obList);
                textField.clear();
            }

            list.setItems(obList);

            event.consume();
        });
    }

    /**
     * Method for making task editing option visible
     *
     * @param button    'edit' button that becomes visible
     * @param list      list view from which an element can be selected
     * @param textField text field to fetch task title from
     */
    public void setShowTaskEditAction(Button button, ListView<String> list, TextField textField) {

        list.setOnContextMenuRequested(event -> {

            String item = list.getSelectionModel().getSelectedItem();
            if (item != null) {
                button.setVisible(true);
                textField.setVisible(true);
                textField.setText(item);
            } else {
                button.setVisible(false);
                textField.setVisible(false);
            }

            event.consume();

        });
    }

    /**
     * Method for saving changes to task title
     *
     * @param button    'edit button' that's clicked
     * @param textField text field to fetch new task title from
     * @param obList    observable list where the change gets saved
     * @param list      list view where the change will be presented
     */
    public void setSaveEditAction(Button button, TextField textField,
                                  ObservableList<String> obList, ListView<String> list) {
        button.setOnAction(event -> {
            String edit = textField.getText();
            // Check if there is something selected AND if the field is not empty
            if (list.getSelectionModel().getSelectedIndex() != -1 && edit.length() >= 1) {
                obList.set(list.getSelectionModel().getSelectedIndex(), edit);
                button.setVisible(false);
                textField.setVisible(false);
            }
            list.setItems(obList);
        });
    }

    /**
     * Method for showing additional (delete, rename) list options
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
            textField.setVisible(true);
            textField.setText(listNameLabel.getText());
            editButton.setVisible(true);
            deleteButton.setVisible(true);

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
     *
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
     * Getter method for obList
     * @return obList
     */
    public ObservableList<String> getObList() {
        return obList;
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
}
