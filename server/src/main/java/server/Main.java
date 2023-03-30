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
package server;

//import MyFXML;
import com.google.inject.Injector;
//import MyModule;
//import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import server.scenes.ServerSideSetUpCtrl;
import javafx.application.Application;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.google.inject.Guice.createInjector;

@SpringBootApplication
@EntityScan(basePackages = { "commons", "server" })
public class Main extends Application{


    private static final Injector INJECTOR = createInjector(new MyModule());
    private static final MyFXML FXML = new MyFXML(INJECTOR);

    /**Main for quotes/person application
     *
     * @param args
     */
    public static void main(String[] args) {
        launch();
        //SpringApplication.run(Main.class, args);
    }

    /**Start of the stage for the application
     *
     * @param primaryStage the primary stage for this application, onto which
     * the application scene can be set.
     * Applications may create other stages, if needed, but they will not be
     * primary stages.
     * @throws IOException
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        var setup = FXML.load(ServerSideSetUpCtrl.class,
                "server", "scenes", "ServerSideSetUp.fxml");
        Stage stage = new Stage();
        stage.setScene(new Scene(setup.getValue()));
        stage.show();
    }

    /**TODO
     *
     * @param port
     */
    public static void launchServer(String port){
        System.out.println("here on port: " + port);
        String address = "";
        try {
            address = InetAddress.getLocalHost().getHostAddress();
        }
        catch (UnknownHostException e){
            System.out.println("Something went wrong");
            System.exit(1);
        }
        System.out.println(address);
        System.setProperty("server.port", port);
        System.setProperty("spring.datasource.url", "jdbc:h2:file:./quizzzz"+port);
        SpringApplication.run(Main.class);

    }

}