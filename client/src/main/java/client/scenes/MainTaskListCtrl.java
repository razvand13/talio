package client.scenes;

import commons.Board;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;



public class MainTaskListCtrl {

    private Stage primaryStage;

    private TaskListCtrl taskListCtrl;
    private Scene taskList;

    private AddCardCtrl addCardCtrl;

    private Scene addCard;

    private ServerConnectCtrl serverConnectCtrl;
    private Scene serverConnect;

    private OverviewOfBoardsCtrl overviewOfBoardsCtrl;
    private Scene overviewOfBoards;

    private Board board;


    /**Method for initializing main controller
     * @param primaryStage stage passed in as primary stage
     * @param taskList for setting up task list scene
     * @param addCard add card scene
     * @param  overviewOfBoards overviewOfBoards scene
     * @param serverConnect server connect scene
     */
    public void initialize(Stage primaryStage, Pair<TaskListCtrl, Parent> taskList,
                           Pair<AddCardCtrl, Parent> addCard,
                           Pair<ServerConnectCtrl, Parent> serverConnect,
                           Pair<OverviewOfBoardsCtrl, Parent> overviewOfBoards) {
        this.primaryStage = primaryStage;

        this.taskListCtrl = taskList.getKey();
        this.taskList = new Scene(taskList.getValue());

        this.addCardCtrl = addCard.getKey();
        this.addCard = new Scene(addCard.getValue());

        this.serverConnectCtrl = serverConnect.getKey();
        this.serverConnect = new Scene(serverConnect.getValue());

        this.overviewOfBoardsCtrl = overviewOfBoards.getKey();
        this.overviewOfBoards = new Scene(overviewOfBoards.getValue());


        showServerConnect();
        primaryStage.show();
    }

    /**
     * Method for displaying TaskListView.fxml
     */
    public void showTaskListView() {
        taskListCtrl.firstTimeSetUp();
        primaryStage.setTitle("Task List");
        primaryStage.setScene(taskList);
    }

    /**
     *
     */
    public void showAdd() {
        addCardCtrl.firstTimeSetUp();
        primaryStage.setTitle("Add card");
        primaryStage.setScene(addCard);
    }

    /**
     *
     */
    public void showServerConnect() {
        primaryStage.setTitle("Choose a port");
        primaryStage.setScene(serverConnect);
    }

    /**
     * Set board of the taskList
     * @param b
     */
    public void setTaskListCtrlBoard(Board b){
        this.board = b;
    }

    /**
     * Show overview of boards
     */
    public void showOverviewOfBoards(){
        overviewOfBoardsCtrl.firstTimeSetUp();
        primaryStage.setTitle("Overview of boards");
        primaryStage.setScene(overviewOfBoards);
    }

}
