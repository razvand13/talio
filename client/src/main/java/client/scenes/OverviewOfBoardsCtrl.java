package client.scenes;

import client.components.BoardContainer;
import client.utils.OurServerUtils;
import com.google.inject.Inject;
import commons.Board;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.TilePane;

import java.io.*;
import java.util.*;


public class OverviewOfBoardsCtrl {
    private final OurServerUtils server;
    private final MainTaskListCtrl mainCtrl;
    private final TaskListCtrl taskListCtrl;
    private final AdminSceneCtrl adminSceneCtrl;

    private List<Board> boards;
    public List<Long> joinedBoardIds;
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
                                TaskListCtrl taskListCtrl,
                                AdminSceneCtrl adminSceneCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.taskListCtrl = taskListCtrl;
        this.adminSceneCtrl = adminSceneCtrl;
        this.boards = new ArrayList<>();
        this.joinedBoardIds = new ArrayList<>();
    }

    /**
     * Method called when "Create New Board" is clicked in board overview.
     * It creates a new board, and switches to its task list overview
     */
    public void newBoard() {
        String title = boardTitle.getText();
        if(title==null || title ==""){
            title = "New Board";
        }
        Board board = new Board(title);

        server.send("/app/boards", board);
        board = server.getMostRecentBoard();

        taskListCtrl.setTaskListCtrlBoard(board);
        mainCtrl.showTaskListView();
        //writeId(board.id);
        joinedBoardIds.add(board.id);
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
                        //writeId(id);
                        joinedBoardIds.add(id);
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
     * Method for setting up the admin button
     */
    public void admin(){
        System.out.println("print boards");
        //mainCtrl.showAdminOverview();
        mainCtrl.showAdminKey();
    }

    /**
     * Method for going back to the serverConnect
     */
    public void serverSelect(){
        mainCtrl.showServerConnect();
    }

    /**
     * Prompts the user with inputting the admin key
     */
    public void promptAdminKey(){
        mainCtrl.showAdminKey();
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
        server.registerForMessages("/topic/remove-board", Board.class, b -> {
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

        for(long l: joinedBoardIds){
            Board board = server.getBoardById(l);
            if(board == null)
                joinedBoardIds.remove(l);
            else
                boards.add(board);
        }
        Collections.reverse(boards);
        for(Board b : boards) {
            BoardContainer boardContainer = new BoardContainer(b, server, mainCtrl, taskListCtrl);
            boardContainer.setParent(boardTilePane);
            boardTilePane.getChildren().add(boardContainer);
        }
    }

    /** Getter for server
     * @return the server
     */
    public OurServerUtils getServer() {
        return server;
    }

    /** Getter for mainCtrl
     * @return the mainCtrl
     */
    public MainTaskListCtrl getMainCtrl() {
        return mainCtrl;
    }

    /** Getter for taskListCtrl
     * @return the taskListCtrl
     */
    public TaskListCtrl getTaskListCtrl() {
        return taskListCtrl;
    }

    /** Getter for adminSceneCtrl
     * @return the adminSceneCtrl
     */
    public AdminSceneCtrl getAdminSceneCtrl() {
        return adminSceneCtrl;
    }

}
