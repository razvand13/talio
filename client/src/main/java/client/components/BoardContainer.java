package client.components;

import client.scenes.MainTaskListCtrl;
import client.utils.OurServerUtils;
import com.google.inject.Inject;
import commons.Board;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class BoardContainer extends VBox {
    private HBox parent;
    private final OurServerUtils server;
    private final MainTaskListCtrl mainCtrl;
    private Board board;
    @FXML
    private Button joinBoardWithId;
    @FXML
    private Text boardNameTextField;
    @FXML
    private Button joinBoardButton;

    /**
     * Constructor for BoardContainer
     * @param board
     * @param server
     * @param mainCtrl
     */
    //@Inject
    public BoardContainer(Board board, OurServerUtils server, MainTaskListCtrl mainCtrl) {
        this.board = board;
        this.server = server;
        this.mainCtrl = mainCtrl;

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

        boardNameTextField.setText(board.title);

        setOpenBoard();
    }

    /**
     * Setter for joinBoardButton;
     */
    public void setOpenBoard(){
        this.joinBoardButton.setOnMouseClicked(event -> {
            mainCtrl.setTaskListCtrlBoard(board);
            mainCtrl.showTaskListView();
            event.consume();
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
     * @return Button
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
}
