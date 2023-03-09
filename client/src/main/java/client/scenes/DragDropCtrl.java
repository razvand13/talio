package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.*;

import java.util.Arrays;



public class DragDropCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    // TODO Lists are hard-coded
    @FXML
    private ListView<String> list1;
    @FXML
    private ListView<String> list2;

    @FXML
    private ListView<String> list3;

    @Inject
    public DragDropCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void refresh(){
        // TODO list items are hard-coded
        var items1 = FXCollections.observableList(Arrays.asList("List 1 Item 1", "List 1 Item 2", "List 1 Item 3", "List 1 Item 4", "List 1 Item 5", "List 1 Item 6", "List 1 Item 7", "List 1 Item 8", "List 1 Item 9", "List 1 Item 10"));
        var items2 = FXCollections.observableList(Arrays.asList("List 2 Item 1", "List 2 Item 2", "List 2 Item 3", "List 2 Item 4", "List 2 Item 5", "List 2 Item 6", "List 2 Item 7", "List 2 Item 8", "List 2 Item 9", "List 2 Item 10"));
        list1.setItems(items1);
        list2.setItems(items2);

//        list1.setOnMouseClicked(e -> System.out.println("Clicked"));
        setHandlers(list1);
        setHandlers(list2);

        for(String item : list1.getItems()) System.out.print(item + " ");
        System.out.println();

        for(String item : list2.getItems()) System.out.print(item + " ");
        System.out.println();
    }
    /**
     * Sets drag and drop event handlers on a ListView
     * Lists are both sources and targets of this operation, so all handlers will be applied on them directly
     *
     * @param list list to apply handlers to
     */
    public void setHandlers(ListView<String> list){
        // Functionality
        list.setOnDragDetected(event -> dragDetected(list, event));
        list.setOnDragOver(event -> dragOver(list, event));
        list.setOnDragDropped(event -> dragDropped(list, event));
        list.setOnDragDone(event -> dragDone(list, event));

        // Styling
        list.setOnDragEntered(event -> dragEntered(list, event));
        list.setOnDragExited(event -> dragExited(list, event));
    }

    /**
     * Adds a dragDetected event handler on a list
     * When a drag is detected, add the contents of the item dragged into the dragboard
     *
     * @param list source list
     * @param event clicking on a list item
     */
    public void dragDetected(ListView<String> list, MouseEvent event){
        String selectedItem = list.getSelectionModel().getSelectedItem();

        Dragboard db = list.startDragAndDrop(TransferMode.MOVE);

        ClipboardContent content = new ClipboardContent();
        content.putString(selectedItem);
        db.setContent(content);

        System.out.println(selectedItem);

        event.consume();
    }

    /**
     * Adds a dragOver event handler on a list
     * When a drag is in progress and the users drags content over another list,
     * be ready to accept that data
     *
     * @param list target list
     * @param event dragging data over another list
     */
    public void dragOver(ListView<String> list, DragEvent event){
        // Only execute if there is data that is being dragged
        if(event.getGestureSource() != list &&
                event.getDragboard().hasString()){
            event.acceptTransferModes(TransferMode.MOVE);
        }

        event.consume();
    }

    /**
     * Adds a dragDropped event handler on a list
     *
     * The success variable is needed if the user drops the data somewhere other than another list.
     * This way the data isn't lost and the drop is not considered completed,
     * preserving the ACID-ity of the operation.
     *
     * @param list target list
     * @param event dropping dragged data
     */
    public void dragDropped(ListView<String> list, DragEvent event){
        Dragboard db = event.getDragboard();
        boolean success = false;

        if(db.hasString()){
            String dbContent = db.getString();
            System.out.println("You dropped " + dbContent);

            // TODO try to cast the list
            var listItems = list.getItems();
            listItems.add(dbContent);
            list.setItems(listItems);

            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
    }

    /**
     * Adds a dragDone event handler on a list
     *
     * Once the drag is over, since the dragDropped(ListView<String>, DragEvent) method offers the new data to that list,
     * the same data from the old list must be removed
     *
     * @param list the list the data was dragged from
     * @param event the drag operation has finished
     */
    public void dragDone(ListView<String> list, DragEvent event){
        String selectedItem = list.getSelectionModel().getSelectedItem();

        if(event.getTransferMode() == TransferMode.MOVE){
            list.getItems().remove(selectedItem);
        }

        event.consume();
    }

    public void dragEntered(ListView<String> list, DragEvent event){
        if(event.getDragboard().hasString()){
            // TODO Add different styling when the user drags an item over a list
            list.setStyle("");
            System.out.println("You can drop anything here!");
        }
    }

    public void dragExited(ListView<String> list, DragEvent event){
        // TODO Remove the styling after the user exits the list premises
        list.setStyle("");
        System.out.println("Sad to see you go...");

        event.consume();
    }

}
