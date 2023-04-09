package client.components;


import client.scenes.MainTaskListCtrl;
import client.utils.HelperFXinit;
import client.utils.OurServerUtils;
import commons.Card;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

class ListContainerTest extends HelperFXinit {

    @Test
    public void testConstructor() {
        OurServerUtils utils = mock(OurServerUtils.class);
        MainTaskListCtrl mainCtrl = mock(MainTaskListCtrl.class);
        ListContainer listContainer = new ListContainer("Test",utils,mainCtrl);
        assertEquals("Test",listContainer.getListNameLabel().getText());

    }
    @Test
    public void testGettersAndSetters(){
        OurServerUtils utils = mock(OurServerUtils.class);
        MainTaskListCtrl mainCtrl = mock(MainTaskListCtrl.class);
        ListContainer listContainer = new ListContainer("Test",utils,mainCtrl);
        //test setParent
        HBox hBox = new HBox();
        listContainer.setParent(hBox);
        assertEquals(hBox,listContainer.getParentOfThis());
    }

    @Test
    public void testSetAddTaskAction() {
        Button buttonMock = new Button();
        TextField textFieldMock = new TextField();
        ListView<String> listMock = new ListView<>();
        listMock.getItems().add("Test");
        OurServerUtils utils = mock(OurServerUtils.class);
        MainTaskListCtrl mainCtrl = mock(MainTaskListCtrl.class);

        ListContainer listContainer = new ListContainer("Test",utils,mainCtrl);
        listContainer.setAddTaskAction(buttonMock, textFieldMock, listMock);

        EventHandler<ActionEvent> eventHandler = buttonMock.getOnAction();
        ActionEvent eventMock = mock(ActionEvent.class);
        eventHandler.handle(eventMock);


        assertEquals("", textFieldMock.getText());
        textFieldMock.setText("Test");
        buttonMock.fire();
        verify(utils).send(eq("/app/cards"),any(Card.class));
    }


}