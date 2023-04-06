package client.scenes;

import client.components.BoardContainer;
import client.components.ListContainer;
import client.utils.OurServerUtils;
import com.google.inject.Inject;
import commons.Board;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class OverviewOfBoardsCtrl {
    private final OurServerUtils server;
    private final MainTaskListCtrl mainCtrl;
    private final List<Board> boards;
    @FXML
    private TextField idTextField;
    @FXML
    private Button joinBoardButton;
    @FXML
    private Button adminButton;
    @FXML
    private Button serverSelectButton;
    @FXML
    private HBox hBoxBoards;
    @FXML
    private Button createNewBoardButton;

    /**Constructorfor OverviewOfBoardsCtrl
     *
     * @param server
     * @param mainCtrl
     */
    @Inject
    public OverviewOfBoardsCtrl(OurServerUtils server, MainTaskListCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.boards = new ArrayList<>();
       // buttonsSetup();
    }

    /**
     * Method for setting up the buttons
     */
    public void buttonsSetup(){
        joinButtonSetUp();
        serverSelectSetUp();
        adminButtonSetup();
    }

    /**
     * Method for setting up the controller for the join board by id
     */
    public void joinButtonSetUp(){
        joinBoardButton.setOnMouseClicked(event -> {
            String idString = idTextField.getText();
            try{
                long id = Long.parseLong(idString);
                if(id > 0){
                    for(Board b : boards){
                        if(b.id == id){
                            mainCtrl.setTaskListCtrlBoard(b);
                            mainCtrl.showTaskListView();
                        }
                    }
                    mainCtrl.showTaskListView();

                }
            } catch (NumberFormatException e){
                System.out.println(e.getStackTrace());
                throw new RuntimeException(e);
            }
            event.consume();
        });
    }

    /**
     * Method for setting up the admin button
     */
    public void adminButtonSetup(){
        this.adminButton.setOnMouseClicked(event -> {
            //load the admin scenes
            event.consume();
        });
    }

    /**
     * Method for going back to the serverConnect
     */
    public void serverSelectSetUp(){
        serverSelectButton.setOnMouseClicked(event -> {
            mainCtrl.showServerConnect();
            event.consume();
        });
    }
    /**
     * First time setup method
     */
    public void firstTimeSetUp(){
        server.setSession();
        server.registerForMessages("/topic/boards", Board.class, b -> {
            boards.add(b);
            Platform.runLater(this::refreshBoards);
        });
    }

    /**
     * Refresh Boards
     */
    public void refreshBoards(){
        clearBoards();
        makeBoards();
    }

    /**
     * Delete all the boards
     */
    public void clearBoards(){
        List<ListContainer> containers = new ArrayList<>();
        for(Node child : hBoxBoards.getChildren()){
            if(child.getClass() == ListContainer.class){ // Error handling
                ListContainer listContainer = (ListContainer) child;
                containers.add(listContainer);
            }
        }
        hBoxBoards.getChildren().removeAll(containers);
    }

    /**
     * Method for making the boardContainers appear
     */
    public void makeBoards(){
        //When the repository and ints controller are done, uncomment the next line
        //boards = server.getBoards();
        boards.add(new Board("board1"));
        for(Board b : boards) {
            BoardContainer boardContainer = new BoardContainer(b, server, mainCtrl);
            boardContainer.setParent(hBoxBoards);
            hBoxBoards.getChildren().add(boardContainer);
        }
    }
}
