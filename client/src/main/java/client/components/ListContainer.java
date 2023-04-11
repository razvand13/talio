package client.components;

import client.scenes.MainTaskListCtrl;
import client.utils.OurServerUtils;
import commons.Card;
import commons.ListOfCards;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
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

    //list of all cards in the database
    private List<Card> allCards;



    /**
     * Constructor for the custom FXML component
     * An FXMLLoader is needed since we load an external component, from a file,
     * not an already existing one from SceneBuilder
     *
     * @param listName the name of the new List
     * @param server
     * @param mainCtrl
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


    /** Method for setting the cards
     * @param allCards list of all cards in the database
     */
    public void setAllCards(List<Card> allCards) {
        this.allCards = allCards;
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
        setDeleteList(this, listDeleteBtn, listRenameField, listEditBtn, list);
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

                Card myCard = new Card(taskInput, listOfCards);
                myCard.position = list.getItems().size();
                myCard.listOfCards = listOfCards;

                server.send("/app/cards", myCard);

                textField.clear();

            }

            event.consume();
        });
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
                delBtn.setVisible(false);
                textField.setVisible(false);
            }

            event.consume();
        });
    }

    /**
     * Method for saving changes to task title
     *
     * @param editBtn    'edit button' that's clicked
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
                editBtn.setVisible(false);
                delBtn.setVisible(false);
                textField.setVisible(false);
            }

            var cards = server.getCardsByListId(listOfCards.id);

            //card passed to into the server
            Card newCard = null;
            for(Card card : cards){
                if(card.position == idx){
                    newCard = card;
                    break;
                }
            }

            if (edit.length() >= 1) {
                newCard.title = edit;
                editBtn.setVisible(false);
                delBtn.setVisible(false);
                textField.setVisible(false);
            }

            event.consume();
            server.send("/app/edit-card", newCard);
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

            var cards = server.getCardsByListId(listOfCards.id);

            //card passed to into the server
            Card delCard = null;
            for(Card card : cards){
                if(card.position == idx){
                    delCard = card;
                    break;
                }
            }


            deleteButton.setVisible(false);
            editButton.setVisible(false);
            textField.setVisible(false);


            server.send("/app/remove-card", delCard);

            //the position of the cards in the list that have higher position
            //than that of the deleted, needs to be updated
            updatePositions(listOfCards.id, delCard.position);

            event.consume();
        });
    }

    /**
     * Method for updating the card positions in a list, after the removal of one of them
     * @param listID id of the list from which the card is deleted
     * @param position position of the deleted card
     */
    public void updatePositions(long listID, int position){

        List<Card> toUpdate = new ArrayList<>();
        List<Card> cards = server.getCardsByListId(listID);
        for(Card card : cards){
            if(card.position > position){
                card.position--;
                toUpdate.add(card);
            }
        }

        for(Card c : toUpdate){
            server.send("/app/edit-card", c);
        }
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
     * Method for deleting the vertical box containing a list. The list can get deleted
     * only if it contains no cards, otherwise an alert is shown to the user
     *
     * @param vBox         vertical box getting deleted
     * @param deleteButton delete button that's clicked
     * @param textField    editing field that needs to be set invisible
     * @param editButton   editing button that needs to be set invisible
     * @param list         list view for checking whether the list is empty
     */
    public void setDeleteList(VBox vBox, Button deleteButton,
                              TextField textField, Button editButton, ListView<String> list) {
        deleteButton.setOnAction(event -> {
            if(list.getItems().size() == 0){
                ListOfCards delList = server.getListById(listOfCards.id);
                server.send("/app/remove-lists", delList);
            }
            else{
                Alert a = new Alert(Alert.AlertType.INFORMATION);

                a.setGraphic(null);
                a.setTitle("Alert");
                a.setHeaderText("The list cannot be deleted as it still contains tasks.");

                a.show();
            }

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
            String edit = textField.getText();

            server.setSession();
            ListOfCards newList = server.getListById(listOfCards.id);

            if (edit.length() >= 1) {
                newList.title = edit;
                editButton.setVisible(false);
                deleteButton.setVisible(false);
                textField.setVisible(false);
            }

            event.consume();
            server.send("/app/edit-lists", newList);
        });
    }


    /**
     * Sets drag and drop event handlers on a ListView
     * Lists are both sources and targets of this operation,
     * so all handlers will be applied on them directly
     * These handlers only work with Strings as the content of lists
     * @param list list to apply handlers to
     */
    private void setDragAndDrop(ListView<String> list) {
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
        long listID = listOfCards.id;
        int idx = list.getItems().indexOf(selectedItem);

        var cards = server.getCardsByListId(listOfCards.id);

        //card passed to into the server
        Card dragCard = null;
        for(Card card : cards){
            if(card.position == idx){
                dragCard = card;
                break;
            }
        }

        if(dragCard != null) {
            ClipboardContent content = new ClipboardContent();
            content.putString(dragCard.id+" "+listID);
            db.setContent(content);
        }

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
            int index = calculateIndex(list, event);
            list.getSelectionModel().clearAndSelect(index);
            list.scrollTo(index);
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
            String[] data =  db.getString().split(" ");
            long cardId = Long.parseLong(data[0]);
            long listId = Long.parseLong(data[1]);


            Card card = server.getCardById(cardId);

            int pos = card.position;
            if(listOfCards.id != listId) {
                card.position = list.getItems().size();
                card.listOfCards = listOfCards;
                server.send("/app/edit-card", card);
                updatePositions(listId, pos);
                success = true;
            }else{
                List<Card> toUpdate = new ArrayList<>();
                int newPos = list.getSelectionModel().getSelectedIndex();
                System.out.println("new position = " + newPos);
                ListOfCards wanted = card.listOfCards;
                if(newPos == -1){
                    card.position = list.getItems().size()-1;
                    System.out.println(list.getItems().size());
                    decrementIndexes(card, pos, list.getItems().size()-1, wanted);
                    toUpdate.add(card);
                }
                else if(pos<newPos) {
                    decrementIndexes(card, pos, newPos, wanted);
                    card.position = newPos;
                    toUpdate.add(card);
                }else if(pos>newPos){
                    incrementIndexes(card, pos, newPos, wanted);
                    card.position = newPos;
                    toUpdate.add(card);
                }

                for(Card update : toUpdate){
                    server.send("/app/edit-card", update);
                }
            }
        }


        event.setDropCompleted(success);
        event.consume();
    }



    /** Method used to shift all cards between the 2 positions by incrementing their index
     * @param card The card we clicked on
     * @param pos The original position of the card
     * @param newPos The new position where we want to add the card
     * @param list the listOfCards belonging to the card
     */
    public void incrementIndexes(Card card, int pos, int newPos, ListOfCards list){
        List<Card> toInc = new ArrayList<>();
        allCards = server.getCards();
        for(int i =0; i < allCards.size(); i++){
            if(allCards.get(i).position>=newPos
                    && allCards.get(i).position<pos
                    && allCards.get(i).listOfCards.equals(list)){
                Card inc = allCards.get(i);
                inc.position = inc.position+1;
                toInc.add(inc);
            }
        }
        for(Card inc : toInc){
            server.send("/app/edit-card", inc);
        }
        card.position = newPos;
        server.send("/app/edit-card", card);
    }

    /** Method used to shift all cards between the 2 positions by decrementing their index
     * @param card The card we clicked on
     * @param pos The original position of the card
     * @param newPos The new position where we want to add the card
     * @param list the listOfCards belonging to the card
     */
    public void decrementIndexes(Card card, int pos, int newPos, ListOfCards list) {
        if (pos < newPos) {
            List<Card> toDec = new ArrayList<>();
            allCards = server.getCards();
            for (int i = 0; i < allCards.size(); i++) {
                if (allCards.get(i).position <= newPos
                        && allCards.get(i).position > pos
                        && allCards.get(i).listOfCards.equals(list)) {
                    Card dec = allCards.get(i);
                    dec.position = dec.position - 1;
                    toDec.add(dec);
                }
            }
            for(Card dec : toDec){
                server.send("/app/edit-card", dec);
            }
            card.position = newPos;
            server.send("/app/edit-card", card);
        }
    }

    /** Method used to update the positions of the cards in the list
     * @param list the list the data was dragged from
     * @param event the drag event
     * @return an int representing the index of the card
     */
    public int calculateIndex(ListView<String> list, DragEvent event){
        double mouseY = event.getY();
        double totalHeight = list.getHeight();
        int index = (int) (mouseY/totalHeight*13);
        if(index<0){
            index = 0;
        }else if(index>= list.getItems().size()){
            index = list.getItems().size();
        }
        return index;
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
     * @return parent
     */
    public HBox getParentOfThis() {
        return parent;
    }

    /**vcvcx
     * @return server
     */
    public OurServerUtils getServer() {
        return server; //get the server
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

    /** Getter method for mainCtrl
     * @return mainCtrl
     */
    public MainTaskListCtrl getMainCtrl() {
        return mainCtrl;
    }

    /**
     * Getter for listOfCards
     * @return the listOfCards associated with this container
     */
    public ListOfCards getListOfCards() {
        return listOfCards;
    }

    /**
     * Setter method for ListOfCards
     * @param loc ListOfCards
     */
    public void setListOfCards(ListOfCards loc){
        listOfCards = loc;
    }



}
