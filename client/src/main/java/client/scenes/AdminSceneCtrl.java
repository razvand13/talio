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

    @Inject
    public AdminSceneCtrl(OurServerUtils server, MainTaskListCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;

//        var boardList = server.getBoards();
//        boards = FXCollections.observableList(boardList);
//        table.setItems(boards);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IDColumn.setCellValueFactory(b ->
                new SimpleStringProperty(b.getValue().getId().toString()));
        boardNameColumn.setCellValueFactory(b ->
                new SimpleStringProperty(b.getValue().title));
        //joinKeyColumn.setCellValueFactory(b -> new SimpleStringProperty(b.getValue().key))

    }

    public void showBoards() {
        server.setSession();
        refresh();
        server.registerForMessages("/topic/boards", Board.class, b -> {
            boards.add(b);
        });

    }

    /**show board overview
     *
     */
    public void back(){
        mainCtrl.showOverviewOfBoards();
    }

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
        /////////////////////////////////////////////////////////////////////

      //  List<Board> serverBoards = server.getBoards();
//        for(int i = 0; i < serverBoards.size(); i++){
//            if(serverBoards.get(i).id == delBoard.id){
//                delBoard = serverBoards.get(i);
//                break;
//            }
//        }

        List<ListOfCards> allLists = server.getLists();
        ListOfCards delList = null;

        for(int i = 0; i < allLists.size(); i++){
            if(allLists.get(i).board.equals(delBoard)){
                delList = allLists.get(i);
                deleteList(delList);
                if(server.getListById(delList.id) != null)
                deleteList(delList);
            }
        }
        System.out.println(delBoard);
        server.send("/app/remove-board", delBoard);
        System.out.println(server.getBoards());
        refresh();
        System.out.println("refresh");
    }

    public void deleteList(ListOfCards list) {
        server.setSession();

        long listID = list.id;

        List<ListOfCards> allLists = server.getLists();
        List<Card> allCards = server.getCards();

        Card delCard = null;
        ListOfCards delList = null;

        //delete all cards in the list
        for(int i = 0; i < allCards.size(); i++) {
            if (allCards.get(i).listOfCards.id == listID) {
                delCard = allCards.get(i);
                System.out.println("card deleted" + delCard.id);
                server.send("/app/remove-card", delCard);
            }
        }

        for(int i = 0; i < allLists.size(); i++) {
            if (allLists.get(i).id == listID) {
                delList = allLists.get(i);
                break;
            }
        }
        server.send("/app/remove-lists", delList);

    }

    public void refresh() {
        var listOfBoards = server.getBoards();
        boards = FXCollections.observableList(listOfBoards);
        table.setItems(boards);
    }

}
