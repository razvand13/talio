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
import javafx.scene.layout.TilePane;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


public class OverviewOfBoardsCtrl {
    private final OurServerUtils server;
    private final MainTaskListCtrl mainCtrl;
    private final TaskListCtrl taskListCtrl;
    private final AdminSceneCtrl adminSceneCtrl;

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
    private Button NewBoardButton;
    @FXML
    private TextField boardTitle;

    @FXML
    private TilePane boardTilePane;

    /**
     * Constructor for OverviewOfBoardsCtrl
     *
     * @param server server
     * @param mainCtrl mainCtrl
     * @param taskListCtrl taskListCtrl
     * @param adminSceneCtrl adminSceneCtrl
     */
    @Inject
    public OverviewOfBoardsCtrl(OurServerUtils server, MainTaskListCtrl mainCtrl,
                                TaskListCtrl taskListCtrl, AdminSceneCtrl adminSceneCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.taskListCtrl = taskListCtrl;
        this.adminSceneCtrl = adminSceneCtrl;
        this.boards = new ArrayList<>();
    }

    /**
     * Method called when "Create New Board" is clicked in board overview.
     * It creates a new board, and switches to its task list overview
     */
    public void newBoard() {
        String title = boardTitle.getText();
        Board board = new Board(title);
        System.out.println("make new board " + board);
        server.send("/app/boards", board);
        board = server.getMostRecentBoard();

        taskListCtrl.setTaskListCtrlBoard(board);
        System.out.println("join board");
        mainCtrl.showTaskListView();
        writeId(board.id);
        boardTitle.clear();

    }


    /**
     * Method for setting up the controller for the join board by id
     */
    public void join(){
        String idString = idTextField.getText();
        try {
            long id = Long.parseLong(idString);
            if (id > 0) {
                List<Board> allBoards = server.getBoards();
                for (Board b : allBoards) {
                    if (b.id == id) {
                        taskListCtrl.setTaskListCtrlBoard(b);
                        System.out.println("join board");
                        mainCtrl.showTaskListView();
                        writeId(id);
                        idTextField.clear();
                        break;
                    }
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
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
    public void admin(){
        System.out.println("print boards");
        mainCtrl.showAdminOverview();
    }

    /**
     * Method for going back to the serverConnect
     */
    public void serverSelect(){
        mainCtrl.showServerConnect();
    }
    /**
     * First time setup method
     */
    public void firstTimeSetUp(){
        server.setSession();
        refreshBoards();
        server.registerForMessages("/topic/boards", Board.class, b -> {
            boards.add(b);
            Platform.runLater(this::refreshBoards);
        });
        server.registerForMessages("/topic/edit-board", Board.class, b -> {
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
        boards.clear();
        List<BoardContainer> containers = new ArrayList<>();
        for(Node child : boardTilePane.getChildren()){
            if(child.getClass() == BoardContainer.class){ // Error handling
                BoardContainer boardContainer = (BoardContainer) child;
                containers.add(boardContainer);
            }
        }
        boardTilePane.getChildren().removeAll(containers);
    }

    /**
     * Method for making the boardContainers appear
     */
    public void makeBoards(){
  
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
            BoardContainer boardContainer = new BoardContainer(b, server, mainCtrl, taskListCtrl);
            boardContainer.setParent(boardTilePane);
            boardTilePane.getChildren().add(boardContainer);
        }
    }
}