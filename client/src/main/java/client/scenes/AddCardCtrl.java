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

import client.utils.OurServerUtils;
import commons.Card;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class AddCardCtrl {

    private final OurServerUtils server;
    private final MainTaskListCtrl mainCtrl;


    @FXML
    private TextField cardTitle;

    @Inject
    public AddCardCtrl(OurServerUtils server, MainTaskListCtrl mainCtrl) {
        this.mainCtrl = mainCtrl;
        this.server = server;

    }

    public void cancel() {
        clearFields();
        mainCtrl.showTaskListView();
    }

    public void ok() {

        server.send("/app/cards", getCard());
        System.out.println("sent to database");
        clearFields();
        mainCtrl.showTaskListView();
    }

    private Card getCard() {
        var title = cardTitle.getText();
        return new Card(title, null, null, null);
    }

    private void clearFields() {
        cardTitle.clear();
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getCode()) {
            case ENTER:
                ok();
                break;
            case ESCAPE:
                cancel();
                break;
            default:
                break;
        }
    }

    public void firstTimeSetUp(){
        server.setSession();
    }
}