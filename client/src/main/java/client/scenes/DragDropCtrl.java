package client.scenes;

import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.*;

import java.util.Arrays;
import java.util.List;


public class DragDropCtrl {

    private final ServerUtils server;
    private final MainCtrl mainCtrl;


    // TODO Lists are hard-coded
    @FXML
    private ListView<String> list1;
    @FXML
    private ListView<String> list2;


    @Inject
    public DragDropCtrl(ServerUtils server, MainCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }

    public void refresh(){
        // TODO list items are hard-coded
        var items1 = FXCollections.observableList(Arrays.asList("List 1 Item 1", "List 1 Item 2", "List 1 Item 3", "List 1 Item 4", "List 1 Item 5", "List 1 Item 6", "List 1 Item 7", "List 1 Item 8", "List 1 Item 9", "List 1 Item 10"));
        var items2 = FXCollections.observableList(Arrays.asList("List 2 Item 1", "List 2 Item 2", "List 2 Item 3", "List 2 Item 4", "List 2 Item 5", "List 2 Item 6", "List 2 Item 7", "List 2 Item 8", "List 2 Item 9", "List 2 Item 10"));

        // DO NOT use .setItems() here, contents will remain the same even after drag
        list1.getItems().addAll(items1);
        list2.getItems().addAll(items2);

        setHandlers(list1);
        setHandlers(list2);
    }
    /**
     * Sets drag and drop event handlers on a ListView
     * Lists are both sources and targets of this operation, so all handlers will be applied on them directly
     * For compatibility with Cards or any other object, remove the code that checks if the data is a String
     * This can cause errors, so take that into account
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
        if(event.getDragboard().hasString()){
            event.acceptTransferModes(TransferMode.MOVE);
        }

        event.consume();
    }

    /**
     * Adds a dragDropped event handler on a list
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

            list.getItems().add(dbContent);

            success = true;
        }

        event.setDropCompleted(success);
        event.consume();
    }

    /**
     * Adds a dragDone event handler on a list
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

    /**
     * Adds a dragEntered event handler on a list
     * When the user is in the process of dragging an item and hovers over a list,
     * that list gets a border shadow
     *
     * @param list target list
     * @param event entering the premise of a list while dragging an item
     */
    public void dragEntered(ListView<String> list, DragEvent event){
        if(event.getDragboard().hasString()){
            list.setStyle("-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.8), 10, 0, 0, 0)");
        }

        event.consume();
    }

    /**
     * Adds a dragEntered event handler on a list
     * When the user is in the process of dragging an item and is no longer
     * hovering of a list, that list's border shadow is removed
     *
     * @param list target list
     * @param event exiting the premise of a list while dragging an item
     */
    public void dragExited(ListView<String> list, DragEvent event){
        list.setStyle("");
        event.consume();
    }

}
