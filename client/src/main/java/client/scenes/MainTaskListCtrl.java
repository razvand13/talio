package client.scenes;

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


    /**Method for initializing main controller
     * @param primaryStage stage passed in as primary stage
     * @param taskList for setting up task list scene
     */
    public void initialize(Stage primaryStage, Pair<TaskListCtrl, Parent> taskList, Pair<AddCardCtrl, Parent> addCard) {
        this.primaryStage = primaryStage;
        this.taskListCtrl = taskList.getKey();
        this.taskList = new Scene(taskList.getValue());
        this.addCardCtrl = addCard.getKey();
        this.addCard = new Scene(addCard.getValue());

        showTaskListView();
        primaryStage.show();
    }

    /**Method for displaying TaskListView.fxml
     */
    public void showTaskListView() {
        taskListCtrl.firstTimeSetUp();
        primaryStage.setTitle("Task List");
        primaryStage.setScene(taskList);
        taskListCtrl.refresh();
    }

    public void showAdd() {
        primaryStage.setTitle("Add card");
        primaryStage.setScene(addCard);
    }

}
