package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;


public class MainTaskListCtrl {

    private Stage primaryStage;

    private TaskListCtrl taskListCtrl;
    private Scene taskList;


    /**Method for initializing main controller
     * @param primaryStage stage passed in as primary stage
     * @param taskList for setting up task list scene
     */
    public void initialize(Stage primaryStage, Pair<TaskListCtrl, Parent> taskList) {
        this.primaryStage = primaryStage;
        this.taskListCtrl = taskList.getKey();
        this.taskList = new Scene(taskList.getValue());

        showTaskListView();
        primaryStage.show();
    }

    /**Method for displaying TaskListView.fxml
     */
    public void showTaskListView() {
        primaryStage.setTitle("Task List");
        primaryStage.setScene(taskList);
        taskListCtrl.refresh();
    }

}
