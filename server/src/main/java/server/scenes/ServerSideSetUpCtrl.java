package server.scenes;

import com.google.inject.Inject;
import javafx.fxml.FXML;
import org.springframework.boot.SpringApplication;
import server.Main;
import javafx.scene.control.TextField;

import java.awt.*;


public class ServerSideSetUpCtrl {

    private final Main main;

    @Inject
    public ServerSideSetUpCtrl(Main main){
        this.main = main;
    }

    @FXML
    private TextField inputPort;
    public void launch(){
        String input = inputPort.getText();
        Main.launchServer(input);
    }
}
