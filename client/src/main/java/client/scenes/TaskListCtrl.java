package client.scenes;

import client.components.ListContainer;
import client.utils.OurServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Card;
import commons.ListOfCards;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.geometry.Insets;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class TaskListCtrl implements Initializable {

    private final OurServerUtils server;
    private final MainTaskListCtrl mainCtrl;
    private List<Card> data;
    private List<ListOfCards> list;

    @FXML
    private HBox hBox;

    @FXML
    private TextField listTitle;

    @FXML
    private Button changeBoardsButton;

    @FXML
    private Text boardIdText;

    private static Board board;

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

        // Reset text
        listTitle.setText("ToDo");

        ListContainer container = new ListContainer(listName, server, mainCtrl);
        ListOfCards myLoc = new ListOfCards(listName, board);

        container.setListOfCards(myLoc);
        server.send("/app/lists", container.getListOfCards());
    }

    /**
     * Method that is called once, on setup
     */
    public void firstTimeSetUp() {
        server.setSession();

        refreshBoard();

        // Add card
//        server.registerForMessages("/topic/cards", Card.class, c -> {
//            data.add(c);
//            Platform.runLater(this::refreshBoard);
//        });

        // Long polling
        // "/api/cards" -> CardController -> "/updates" -> getUpdates()
        server.registerForUpdates("/api/cards/updates", Card.class, c ->
                Platform.runLater(this::refreshBoard));

        // Add list
        server.registerForMessages("/topic/lists", ListOfCards.class, l -> {
            list.add(l);
            Platform.runLater(this::refreshBoard);
        });

        // todo Edit card
        server.registerForMessages("/topic/edit-card", Card.class, c -> {
            Platform.runLater(this::refreshBoard);
        });

        // Remove card
        server.registerForMessages("/topic/remove-card", Card.class, c -> {
            Platform.runLater(this::refreshBoard);
        });

        // todo Remove list
        server.registerForMessages("/topic/remove-lists", ListOfCards.class, loc -> {
            Platform.runLater(this::refreshBoard);
        });

        // todo Edit list
        server.registerForMessages("/topic/edit-lists", ListOfCards.class, loc -> {
            Platform.runLater(this::refreshBoard);
        });

    }

    /**
     * Propagates stop method from OurServerUtils
     * Method that stops the program, including EXEC's thread
     */
    public void stop(){
        server.stop();
        // todo Remove list

        changeBoardSetup();
        //showBoardId();
    }

    /**
     * Setup for changing the board
     */
    public void changeBoardSetup(){
        changeBoardsButton.setOnMouseClicked(event -> {
            mainCtrl.showOverviewOfBoards();
            event.consume();
        });
    }

    /**
     * Editing the board Id text to show the id of the current board;
     */
    public void showBoardId(){
        boardIdText.setText("Board Id: " + board.id);
    }

    /**
     * Method that refreshes the board
     * First removes all lists and their contents, and using
     * the data and lists from the server,
     * redraws them, one by one
     */
    public void refreshBoard(){
        clearBoard();
        makeBoard();
    }

    /**
     * Erase all lists from the board
     */
    public void clearBoard(){
        List<ListContainer> containers = new ArrayList<>();
        for(Node child : hBox.getChildren()){
            if(child.getClass() == ListContainer.class){ // Error handling
                ListContainer listContainer = (ListContainer) child;
                containers.add(listContainer);
            }
        }
        hBox.getChildren().removeAll(containers);
    }

    /**
     * Make the board using 'data' and 'list'
     */
    public void makeBoard(){

        //Redraw lists
        list = server.getLists();
        for(ListOfCards loc : list){
            if(loc.getBoard() == null) {
                System.out.println("makeBoard null");
            } else if(loc.getBoard().equals(this.board)) {
                System.out.println("not null");
                ListContainer listContainer = new ListContainer(loc.title, server, mainCtrl);
                listContainer.setListOfCards(loc);
                listContainer.setParent(hBox);
                hBox.getChildren().add(listContainer);
            }
        }

        //Redraw list contents
        data = server.getCards();
        for(Node child : hBox.getChildren()){
            if(child.getClass() == ListContainer.class){ // Error handling
                ListContainer listContainer = (ListContainer) child;
                ListOfCards listOfCards = listContainer.getListOfCards();

                // Add back each card to their own list
                var cardsTitles = server
                        .getCardsByListId(listOfCards.id)
                        .stream()
                        .map(c -> c.title)
                        .collect(Collectors.toList()); // toList() doesn't work
                listContainer.getList()
                        .getItems()
                        .addAll(cardsTitles);
            }
        }
    }

    /**
     * Shows the server connection screen so the client can pick a different server
     */
    public void disconnect() {
        mainCtrl.showServerConnect();
    }

    /**
     * Setter for board
     * @param b
     */

    public void setTaskListCtrlBoard(Board b) {
        this.board = b;
    }

    /**
     * admin button to go to admin panel
     */
    public void admin() {
        mainCtrl.showAdminKey();
    }
}
