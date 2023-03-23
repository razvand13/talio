package client.scenes;

import client.utils.ServerUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.util.Arrays;

public class ListContainerCtrl extends VBox {

    private final ServerUtils server;
    private final MainTaskListCtrl mainCtrl;
    @FXML
    private Label listNameLabel;
    @FXML
    private ListView<String> list;
    private ObservableList<String> obList;
    @FXML
    private Button addTaskBtn;
    @FXML
    private TextField taskInputField;
    @FXML
    private Button taskEditBtn;
    @FXML
    private TextField taskEditField;
    @FXML
    private Button listOptionsBtn;
    @FXML
    private Button listDeleteBtn;
    @FXML
    private Button listEditBtn;
    @FXML
    private TextField listRenameField;
    private static int listNo = 1;


    public ListContainerCtrl(ServerUtils server, MainTaskListCtrl mainCtrl){
        this.server = server;
        this.mainCtrl = mainCtrl;

        // TODO Connect to FXML file properly
//        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../client.scenes/ListContainer.fxml"));
//        fxmlLoader.setRoot(this);
//        fxmlLoader.setController(ListContainerCtrl.this);
//
//        try{
//            fxmlLoader.load();
//        } catch (Exception e){
//            e.printStackTrace();
//        }

        initFXML();

        // TODO Maybe someone has a better idea?
        this.getChildren()
                .addAll(Arrays.asList(listNameLabel, list, addTaskBtn, taskInputField, taskEditBtn,
                        taskEditField, listOptionsBtn, listDeleteBtn, listEditBtn, listRenameField));
    }

    private void initFXML(){
        this.setMinWidth(200);
        listNameLabel.setText("List " + listNo); // TODO Test this functionality
        listNo++;
        obList = FXCollections.observableArrayList();

        // Code below has been refactored using SceneBuilder
//        addTaskBtn.setText("Add new task");

//        taskEditBtn.setText("Edit");
//        taskEditBtn.setVisible(false);
//        taskEditField.setVisible(false);

//        listOptionsBtn.setText("List options");
//        listDeleteBtn.setText("Delete list");
//        listDeleteBtn.setVisible(false);
//        listEditBtn.setText("Edit list name");
//        listEditBtn.setVisible(false);
//        listRenameField.setVisible(false);
    }


    // TODO Add JavaDoc

    public Label getListNameLabel() {
        return listNameLabel;
    }

    public ListView<String> getList() {
        return list;
    }

    public ObservableList<String> getObList() {
        return obList;
    }

    public Button getAddTaskBtn() {
        return addTaskBtn;
    }

    public TextField getTaskInputField() {
        return taskInputField;
    }

    public Button getTaskEditBtn() {
        return taskEditBtn;
    }

    public TextField getTaskEditField() {
        return taskEditField;
    }

    public Button getListOptionsBtn() {
        return listOptionsBtn;
    }

    public Button getListDeleteBtn() {
        return listDeleteBtn;
    }

    public Button getListEditBtn() {
        return listEditBtn;
    }

    public TextField getListRenameField() {
        return listRenameField;
    }
}
