package client.scenes;

import client.utils.OurServerUtils;
import com.google.inject.Inject;
import commons.Board;
import commons.Card;
import commons.ListOfCards;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

public class AdminSceneCtrl implements Initializable {

    private final OurServerUtils server;
    private final MainTaskListCtrl mainCtrl;

    @FXML
    public TableView<Board> table;
    @FXML
    public TableColumn<Board, String> IDColumn;
    @FXML
    public TableColumn<Board, String> boardNameColumn;
    @FXML
    public TableColumn<Board, String> joinKeyColumn;
    @FXML
    public Button deleteButton;
    @FXML
    public Button backButton;

    private ObservableList<Board> boards;

    /**
     * Constructor for admin scene
     * @param server OurServerUtils
     * @param mainCtrl MainTaskListCtrl
     */
    @Inject
    public AdminSceneCtrl(OurServerUtils server, MainTaskListCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    /**
     * Initializer
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
        IDColumn.setCellValueFactory(b ->
                new SimpleStringProperty(b.getValue().getId().toString()));
        boardNameColumn.setCellValueFactory(b ->
                new SimpleStringProperty(b.getValue().title));
    }

    /**
     * shows the boards currently on the server
     */
    public void showBoards() {
        server.setSession();
        refresh();
        server.registerForMessages("/topic/boards", Board.class, b -> {
            boards.add(b);
        });

    }

    /**
     * show board overview
     */
    public void back(){
        mainCtrl.showOverviewOfBoards();
    }

    /** Deletes the selected board from the database,
     * including all lists and cards assiciated with that bard
     *
     */
    public void deleteBoard(){
        server.setSession();

        //whole part for getting the ID from the selected board in the table
        String board = String.valueOf(this.table.getSelectionModel().getSelectedItem());
        board = board.substring(10,board.length()-1);
        String id = board;

        Scanner s = new Scanner(board);
        s.useDelimiter(",");
        id = s.next();

        Board delBoard = server.getBoardById(Long.parseLong(id));


        List<ListOfCards> allLists = server.getLists();
        ListOfCards delList = server.getListByBoardId(Long.parseLong(id));

        for(int i = 0; i < allLists.size(); i++){
            if(allLists.get(i).board.equals(delBoard)){
                delList = allLists.get(i);
                deleteList(delList);
                if(server.getListById(delList.id) != null)
                deleteList(delList);
            }
        }
        server.send("/app/remove-board", delBoard);
        refresh();
    }

    /**
     * method to delete all the cards from a list, then delete the list
     * without using button event
     * @param list to be deleted
     */
    public void deleteList(ListOfCards list) {
        server.setSession();

        Card delCard = new Card(null);
        ListOfCards delList = server.getListByBoardId(list.id);

        //delete all cards in the list
        while(delCard != null) {
            delCard = server.getCardByListId(list.id);
            server.send("/app/remove-card", delCard);
        }
        server.send("/app/remove-lists", delList);
    }

    /**
     * show changes (remove deleted list) from the board table
     */
    public void refresh() {
        var listOfBoards = server.getBoards();
        boards = FXCollections.observableList(listOfBoards);
        table.setItems(boards);
    }

}
