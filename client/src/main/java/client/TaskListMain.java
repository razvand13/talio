/*
 * Copyright 2021 Delft University of Technology
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package client;

import static com.google.inject.Guice.createInjector;

import java.io.IOException;
import java.net.URISyntaxException;

import client.scenes.MainTaskListCtrl;
import client.scenes.TaskListCtrl;
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

        var mainTaskCtrl = INJECTOR.getInstance(MainTaskListCtrl.class);


        mainTaskCtrl.initialize(primaryStage, taskList);
    }
}