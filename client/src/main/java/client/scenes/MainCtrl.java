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
package client.scenes;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Pair;

public class MainCtrl {

    private Stage primaryStage;

    private QuoteOverviewCtrl overviewCtrl;
    private Scene overview;

    private AddQuoteCtrl addCtrl;
    private Scene add;

    private ServerConnectCtrl serverConnectCtrl;
    private Scene serverConnect;

    /**Method to initialise the primaryStage,
     * overview, addQuoteCtrl and serverConnect
     *
     * @param primaryStage
     * @param overview
     * @param add
     * @param serverConnect
     */
    public void initialize(Stage primaryStage, Pair<QuoteOverviewCtrl, Parent> overview,
            Pair<AddQuoteCtrl, Parent> add,
            Pair<ServerConnectCtrl, Parent> serverConnect,
            Pair<TaskListCtrl, Parent> taskList) {

        this.primaryStage = primaryStage;

        this.overviewCtrl = overview.getKey();
        this.overview = new Scene(overview.getValue());

        this.addCtrl = add.getKey();
        this.add = new Scene(add.getValue());

        this.serverConnectCtrl = serverConnect.getKey();
        this.serverConnect = new Scene(serverConnect.getValue());

        this.taskListCtrl = taskList.getKey();
        this.taskList = new Scene(taskList.getValue());

        showServerConnect();
        primaryStage.show();
    }

    /**Method that initialises the Overview of the Quotes
     *
     */
    public void showOverview() {
        primaryStage.setTitle("Quotes: Overview");
        primaryStage.setScene(overview);
        overviewCtrl.refresh();
    }

    /**Method that initialises the add button
     *
     */
    public void showAdd() {
        primaryStage.setTitle("Quotes: Adding Quote");
        primaryStage.setScene(add);
        add.setOnKeyPressed(e -> addCtrl.keyPressed(e));
    }

    /**Method to choose a port
     *
     */
    public void showServerConnect() {
        primaryStage.setTitle("Choose a port");
        primaryStage.setScene(serverConnect);
    }

    public void showTaskList() {
        primaryStage.setTitle("Task List");
        primaryStage.setScene(taskList);
    }
}
