package client;

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.*;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;

public class TaskListMain extends Application {

    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**Main method for initialization
     *
     * @param args command line arguments
     * @throws URISyntaxException exception if string can't be parsed
     * @throws IOException exception caused by i/o
     */
    public static void main(String[] args) throws URISyntaxException, IOException {
        launch();
    }

    /**
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException exception cause by i/o
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        var taskList = FXML.load(TaskListCtrl.class, "client", "scenes", "TaskListView.fxml");

        var addCard = FXML.load(AddCardCtrl.class, "client", "scenes", "AddCard.fxml");

        var setup = FXML.load(ServerConnectCtrl.class, "client", "scenes", "ServerConnect.fxml");

        var overviewOfBoards = FXML.load(OverviewOfBoardsCtrl.class,
                "client","scenes","OverviewOfBoards.fxml");

        var mainTaskCtrl = INJECTOR.getInstance(MainTaskListCtrl.class);

        mainTaskCtrl.initialize(primaryStage, taskList, addCard, setup,overviewOfBoards);
    }
}
