package client.scenes;

import client.utils.OurServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

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

        this.boards = server.getBoards();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        IDColumn.setCellValueFactory(b ->
                new SimpleStringProperty(b.getValue().id));
        boardNameColumn.setCellValueFactory(b ->
                new SimpleStringProperty(b.getValue().title));
        //joinKeyColumn.setCellValueFactory(b -> new SimpleStringProperty(b.getValue().key));

        server.setSession();
        server.registerForMessages("/topic/quotes", Board.class, b -> {
            boards.add(b);
       });
    }

    /**show board overview
     *
     */
    public void back(){
        // TODO mainCtrl.showBoardOverview();
    }

    public void deleteBoard(Button deleteButton, ListView<String> table){
        deleteButton.setOnAction(event -> {
            server.setSession();
            int idx = boards.getSelectionModel().getSelectedIndex();

            boards = server.getBoards();
            Board delBoard = null;

            for(int i = 0; i < boards.size(); i++){
                if(boards.get(i).position == idx){
                    delBoard = boards.get(i);
                    i = boards.size()+1;
                }
            }

            server.send("/app/remove-board", delBoard);
            event.consume();
        });
    }

    public void refresh() {
        var listOfBoards = server.getBoards();
        boards = FXCollections.observableList(listOfBoards);
        table.setItems(boards);
    }


}
