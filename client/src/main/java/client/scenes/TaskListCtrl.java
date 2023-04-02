package client.scenes;

import client.components.ListContainer;
import client.utils.ServerUtils;
import com.google.inject.Inject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

public class TaskListCtrl implements Initializable {

    private final ServerUtils server;
    private final MainTaskListCtrl mainCtrl;

    @FXML
    private HBox hBox;

    @FXML
    private TextField listTitle;

    /**
     * Constructor method
     *
     * @param server   server, which the application uses
     * @param mainCtrl main controller
     */
    @Inject
    public TaskListCtrl(ServerUtils server, MainTaskListCtrl mainCtrl) {
        this.server = server;
        this.mainCtrl = mainCtrl;
    }


    /**
     * Method for initialization
     *
     * @param location  The location used to resolve relative paths for the root object, or
     *                  {@code null} if the location is not known.
     * @param resources The resources used to localize the root object, or {@code null} if
     *                  the root object was not localized.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hBox.setSpacing(10);
        hBox.setPadding(new Insets(10, 10, 10, 10));
    }

    /**
     * Method called when 'add list' button is clicked
     * Creates a custom ListContainer FXML component with proper functionality
     */
    public void addNewList() {
        // Don't allow empty names, use default
        String listName = listTitle.getText();
        if(listName.equals("")) listName = "ToDo";

        ListContainer container = new ListContainer(listName);

        // Reset text
        listTitle.setText("ToDo");

        //method for overview and edition of card gets assigned here
        //to omit passing MainTaskListCtrl into ListContainer
        setShowCardView(container.getList());

        container.setParent(hBox);
        hBox.getChildren().add(container);
    }

    /**
     * Method that gets called if the user double-clicks on of the tasks
     * on a list. If the user clicked on an existing task, they get
     * forwarded to the card overview screen
     * @param list ListView from which the card is selected
     */
    public void setShowCardView(ListView<String> list) {
        list.setOnMouseClicked(event -> {
            String item = list.getSelectionModel().getSelectedItem();

            if (item != null) {
                if(event.getClickCount() > 1){
                    mainCtrl.showCardView();
                }
            }

            event.consume();
        });
    }

}
