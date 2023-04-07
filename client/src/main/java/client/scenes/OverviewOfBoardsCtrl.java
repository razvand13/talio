package client.scenes;

import client.components.BoardContainer;
import client.utils.OurServerUtils;
import com.google.inject.Inject;
import commons.Board;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class OverviewOfBoardsCtrl {
    private final OurServerUtils server;
    private final MainTaskListCtrl mainCtrl;
    private List<Board> boards;
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
                    List<Board> allBoards = server.getBoards();
                    for(Board b : allBoards){
                        if(b.id == id){
                            mainCtrl.setTaskListCtrlBoard(b);
                            mainCtrl.showTaskListView();
                            writeId(id);
                            break;
                        }
                        throw new RuntimeException();//no board found
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
     * method for writing joined board's id to a file
     * @param id the id of the board
     */
    public void writeId(long id) {
        // can't have : in a filename so replace that with _
        String currentConnection = server.getAddress().replace(":","_");
        File boardIdListFile = new File("TalioJoinedBoardsOn"+currentConnection+".txt");
        //check if board was joined before
        try{
            Scanner fileScanner = new Scanner( boardIdListFile);
            while (fileScanner.hasNextLong()){
                if(fileScanner.nextLong()== id) return;
            }
        }catch (FileNotFoundException ignored){}

        try {
            Writer writer = new FileWriter(boardIdListFile, true);
            writer.write(id+" ");
            writer.close();
        }
        catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error writing to file");
            alert.setHeaderText("unable to save board id");
            alert.setContentText("We're currently unable to save this id to your pc:"+
                            "\n"+e);
            alert.show();
        }
    }

    /**
     * Method for setting up the admin button
     */
    public void adminButtonSetup(){
        mainCtrl.showAdminOverview();
    }

    /**
     * Method for going back to the serverConnect
     */
    public void serverSelectSetUp(){
        System.out.println("here");
        serverSelectButton.setOnMouseClicked(event -> {
            mainCtrl.showServerConnect();
            event.consume();
        });
    }
    /**
     * First time setup method
     */
    public void firstTimeSetUp(){
        refreshBoards();
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
        List<BoardContainer> containers = new ArrayList<>();
        for(Node child : hBoxBoards.getChildren()){
            if(child.getClass() == BoardContainer.class){ // Error handling
                BoardContainer boardContainer = (BoardContainer) child;
                containers.add(boardContainer);
            }
        }
        hBoxBoards.getChildren().removeAll(containers);
    }

    /**
     * Method for making the boardContainers appear
     */
    public void makeBoards(){
        ///this next bit is fully unnecessary I think///
        //When the repository and ints controller are done, uncomment the next line
        //boards = server.getBoards();
        //boards.add(new Board("board1"));
  
        String currentConnection = server.getAddress().replace(":","_");
        try {
            Scanner boardScanner = new Scanner(
                    new File("TalioJoinedBoardsOn"+currentConnection+".txt"));
            while (boardScanner.hasNextLong()){
                long id =boardScanner.nextLong();
                boards.add(server.getBoardById(id));
            }
            Collections.reverse(boards); //the most recent boards at start of list
        }
        //necessary for scanner construction, but iff file isn't found boards should be empty
        //since it means this client hasn't joined any boards yet, so we can ignore the exception
        catch (FileNotFoundException ignored){}
        for(Board b : boards) {
            BoardContainer boardContainer = new BoardContainer(b, server, mainCtrl);
            boardContainer.setParent(hBoxBoards);
            hBoxBoards.getChildren().add(boardContainer);
        }
    }
}
