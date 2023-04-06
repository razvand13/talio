package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;


public class MainTaskListCtrl {

    private Stage primaryStage;

    private TaskListCtrl taskListCtrl;
    private Scene taskList;


    private Scene serverConnect;


    /**Method for initializing main controller
     * @param primaryStage stage passed in as primary stage
     * @param taskList for setting up task list scene
     * @param serverConnect server connect scene
     */
    public void initialize(Stage primaryStage, Pair<TaskListCtrl, Parent> taskList,
                           Pair<ServerConnectCtrl, Parent> serverConnect) {
        this.primaryStage = primaryStage;

        this.taskListCtrl = taskList.getKey();
        this.taskList = new Scene(taskList.getValue());


        this.serverConnect = new Scene(serverConnect.getValue());

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

}
