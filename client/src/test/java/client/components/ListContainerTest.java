package client.components;


import client.scenes.MainTaskListCtrl;
import client.utils.HelperFXinit;
import client.utils.OurServerUtils;
import commons.Card;
import commons.ListOfCards;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.junit.jupiter.api.Test;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;


class ListContainerTest extends HelperFXinit {

    @Test
    public void testConstructor() {
        OurServerUtils utils = mock(OurServerUtils.class);
        MainTaskListCtrl mainCtrl = mock(MainTaskListCtrl.class);
        ListContainer listContainer = new ListContainer("Test", utils, mainCtrl);
        assertEquals("Test", listContainer.getListNameLabel().getText());

    }

    @Test
    public void testGettersAndSetters() {
        OurServerUtils utils = mock(OurServerUtils.class);
        MainTaskListCtrl mainCtrl = mock(MainTaskListCtrl.class);
        ListContainer listContainer = new ListContainer("Test", utils, mainCtrl);
        //test setParent
        assertEquals(listContainer.getServer(), utils);
        assertEquals(listContainer.getMainCtrl(), mainCtrl);
        HBox hBox = new HBox();
        listContainer.setParent(hBox);
        assertEquals(hBox, listContainer.getParentOfThis());

    }

    @Test
    public void testSetAddTaskAction() {
        Button buttonMock = new Button();
        TextField textFieldMock = new TextField();
        ListView<String> listMock = new ListView<>();
        listMock.getItems().add("Test");
        OurServerUtils utils = mock(OurServerUtils.class);
        MainTaskListCtrl mainCtrl = mock(MainTaskListCtrl.class);

        ListContainer listContainer = new ListContainer("Test", utils, mainCtrl);
        listContainer.setAddTaskAction(buttonMock, textFieldMock, listMock);

        EventHandler<ActionEvent> eventHandler = buttonMock.getOnAction();
        ActionEvent eventMock = mock(ActionEvent.class);
        eventHandler.handle(eventMock);
        assertEquals("", textFieldMock.getText());
        textFieldMock.setText("Test");
        buttonMock.fire();
        verify(utils).send(eq("/app/cards"), any(Card.class));
    }

    @Test
    public void testIncrementIndexes() {
        // Create a mock server
        OurServerUtils server = mock(OurServerUtils.class);

        // Create a mock card and list
        Card card = new Card("Card 1", new ListOfCards("List 1"));
        int pos = 0;
        int newPos = 1;
        ListOfCards list = new ListOfCards("List 1");

        // Create a mock list of cards
        List<Card> allCards = new ArrayList<>();
        allCards.add(card);

        // Set up the behavior of the server mock to capture the edited cards
        ArgumentCaptor<String> pathCaptor = ArgumentCaptor.forClass(String.class);
        ArgumentCaptor<Card> cardCaptor = ArgumentCaptor.forClass(Card.class);
        doNothing().when(server).send(pathCaptor.capture(), cardCaptor.capture());

        // Create a new list container and set its allCards list
        ListContainer container = new ListContainer("Test List", server, null);
        container.setAllCards(allCards);

        // Call the method being tested
        container.incrementIndexes(card, pos, newPos, list);

        // Verify that the correct cards were incremented and edited
        assertEquals(1, card.position);
        assertEquals(1, cardCaptor.getValue().position);
        assertEquals("/app/edit-card", pathCaptor.getValue());
    }

    @Test
    public void testGetList() {
        OurServerUtils utils = mock(OurServerUtils.class);
        MainTaskListCtrl mainCtrl = mock(MainTaskListCtrl.class);
        ListContainer listContainer = new ListContainer("Test", utils, mainCtrl);
        assertNotNull(listContainer.getList());
    }

    @Test
    public void testGetListOfCards() {
        OurServerUtils utils = mock(OurServerUtils.class);
        MainTaskListCtrl mainCtrl = mock(MainTaskListCtrl.class);
        ListContainer listContainer = new ListContainer("Test", utils, mainCtrl);
        assertNotNull(listContainer.getListOfCards());
    }

    @Test
    public void testSetListOfCards() {
        OurServerUtils utils = mock(OurServerUtils.class);
        MainTaskListCtrl mainCtrl = mock(MainTaskListCtrl.class);
        ListContainer listContainer = new ListContainer("Test", utils, mainCtrl);

        ListOfCards newListOfCards = new ListOfCards("New List");
        listContainer.setListOfCards(newListOfCards);

        assertEquals(newListOfCards, listContainer.getListOfCards());
    }




}




