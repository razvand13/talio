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

    private Scene addCard;

    private ServerConnectCtrl serverConnectCtrl;
    private Scene serverConnect;

    private AdminSceneCtrl adminSceneCtrl;
    private Scene adminOverview;

    private OverviewOfBoardsCtrl overviewOfBoardsCtrl;
    private Scene overviewOfBoards;

    private AdminKeyCtrl adminKeyCtrl;
    private Scene adminKey;

    private Board board;



    /**
     * Method for initializing main controller
     *
     * @param primaryStage  stage passed in as primary stage
     * @param taskList      for setting up task list scene
     * @param serverConnect server connect scene
     * @param  overviewOfBoards overviewOfBoards scene
     * @param adminOverview admin overview scene
     * @param adminKey admin key scene
     */
    public void initialize(Stage primaryStage, Pair<TaskListCtrl, Parent> taskList,
                           Pair<ServerConnectCtrl, Parent> serverConnect,
                           Pair<AdminSceneCtrl, Parent> adminOverview,
                           Pair<OverviewOfBoardsCtrl, Parent> overviewOfBoards,
                           Pair<AdminKeyCtrl, Parent> adminKey) {
        this.primaryStage = primaryStage;

        this.taskListCtrl = taskList.getKey();
        this.taskList = new Scene(taskList.getValue());

        this.serverConnectCtrl = serverConnect.getKey();
        this.serverConnect = new Scene(serverConnect.getValue());

        this.adminSceneCtrl = adminOverview.getKey();
        this.adminOverview = new Scene(adminOverview.getValue());

        this.overviewOfBoardsCtrl = overviewOfBoards.getKey();
        this.overviewOfBoards = new Scene(overviewOfBoards.getValue());

        this.adminKeyCtrl = adminKey.getKey();
        this.adminKey = new Scene(adminKey.getValue());

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
    public void showServerConnect() {
        primaryStage.setTitle("Choose a port");
        primaryStage.setScene(serverConnect);
    }

    /**
     * connection to admin overview
     */
    public void showAdminOverview() {
        adminSceneCtrl.showBoards();
        primaryStage.setTitle("Admin Overview");
        primaryStage.setScene(adminOverview);
    }

    /**
     * Set board of the taskList
     * @param b
     */
//    public void setTaskListCtrlBoard(Board b){
//        taskListCtrl.setTaskListCtrlBoard(b);
//        this.board = b;
//        System.out.println(board.id);
//    }

    /**
     * Show overview of boards
     */
    public void showOverviewOfBoards(){
        overviewOfBoardsCtrl.firstTimeSetUp();
        primaryStage.setTitle("Overview of boards");
        primaryStage.setScene(overviewOfBoards);
    }

    /**
     * refresh for board overview
     */
    public void updateOverviewOfBoards(){
        overviewOfBoardsCtrl.refreshBoards();
    }

    /**
     * Show admin key scene
     */
    public void showAdminKey(){
        primaryStage.setTitle("Admin key");
        primaryStage.setScene(adminKey);
    }

}
