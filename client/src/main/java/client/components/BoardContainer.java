package client.components;

import client.scenes.MainTaskListCtrl;
import client.scenes.TaskListCtrl;
import client.utils.OurServerUtils;
import commons.Board;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.*;
import java.nio.file.Files;

public class BoardContainer extends VBox {
    private HBox parent;
    private final OurServerUtils server;
    private final MainTaskListCtrl mainCtrl;
    private final TaskListCtrl taskListCtrl;

    private Board board;
    @FXML
    private Button joinBoardWithId;
    @FXML
    private Text boardNameTextField;
    @FXML
    private Label idLabel;
    @FXML
    private Button joinBoardButton;
    @FXML
    private Button removeBoardButton;

    /**
     * Constructor for BoardContainer
     * @param board
     * @param server
     * @param mainCtrl
     * @param taskListCtrl
     */
    public BoardContainer(Board board, OurServerUtils server, MainTaskListCtrl mainCtrl,
                          TaskListCtrl taskListCtrl) {
        this.board = board;
        this.server = server;
        this.mainCtrl = mainCtrl;
        this.taskListCtrl = taskListCtrl;

        FXMLLoader fxmlLoader = new FXMLLoader(getClass()
                .getResource("/client/components/BoardContainer.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try{
            fxmlLoader.load();
        }catch (IOException e){
            System.out.println("HERE!!!!!!!!!!!!!!!!" + "\n");
            throw new RuntimeException(e);
        }

        //this.setMinWidth(200);
        if(board != null) {
            boardNameTextField.setText(board.title);
            idLabel.setText("ID: " + board.id);
            setRemoveBoard();
            setOpenBoard();
        }
    }

    /**
     * Setter for joinBoardButton;
     */
    public void setOpenBoard(){
        this.joinBoardButton.setOnMouseClicked(event -> {
            taskListCtrl.setTaskListCtrlBoard(board);
            mainCtrl.showTaskListView();
            event.consume();
        });
    }


    /**
     * removes this board for this user
     * does NOT delete the board form the database
     */
    public void setRemoveBoard(){
        this.removeBoardButton.setOnMouseClicked(event -> {
            long id = board.id;
            String currentConnection = server.getAddress().replace(":","_");
            File boardIdListFile = new File("TalioJoinedBoardsOn"+currentConnection+".txt");
            try{
                String newIds = Files.readString(boardIdListFile.toPath()).replace(id+" ", "");
                Writer writer = new FileWriter(boardIdListFile, false);
                writer.write(newIds);
                writer.close();
                mainCtrl.updateOverviewOfBoards();
            }
            catch (IOException e){
                System.out.println(e.getStackTrace());
            }
        });
    }

    /**
     * Setter for parent
     * @param parent
     */
    public void setParent(HBox parent) {
        this.parent = parent;
    }

    /**
     * Getter for joinBoardButtonWithId
     * @return button
     */
    public Button getJoinBoard() {
        return joinBoardWithId;
    }

    /**
     * Setter for joinBoardButton
     * @param joinBoardWithId
     */
    public void setJoinBoard(Button joinBoardWithId) {
        this.joinBoardWithId = joinBoardWithId;
    }

    /**
     * Getter for textInBoard
     * @return  Text
     */
    public Text getBoardNameTextField() {
        return boardNameTextField;
    }

    /**
     * Setter for textInBoard
     * @param boardNameTextField
     */
    public void setBoardNameTextField(Text boardNameTextField) {
        this.boardNameTextField = boardNameTextField;
    }

    /**
     * Getter for board
     * @return Board
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Setter for board
     * @param board
     */
    public void setBoard(Board board) {
        this.board = board;
    }

    /**
     * Getter for joinBoardButton
     * @return JoinButton
     */
    public Button getJoinBoardButton() {
        return joinBoardButton;
    }

    /**
     * Setter for joinBoardButton
     * @param joinBoardButton
     */
    public void setJoinBoardButton(Button joinBoardButton) {
        this.joinBoardButton = joinBoardButton;
    }

    /**
     * getter for remove board button
     * @return RemoveButton
     */
    public Button getRemoveBoardButton() {
        return removeBoardButton;
    }

    /**
     * setter for remove board button
     * @param removeBoardButton
     */
    public void setRemoveBoardButton(Button removeBoardButton) {
        this.removeBoardButton = removeBoardButton;
    }
}
