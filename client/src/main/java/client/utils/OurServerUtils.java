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
package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.lang.reflect.Type;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import commons.ListOfCards;
import commons.Quote;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class OurServerUtils {


    private static String SERVER = "http://localhost:8080";


    /**
     * @param address address to connect to
     * Set the SERVER variable to the input value
     */
    public static void setSERVER(String address){
        SERVER =address;
    }

    /**
     * trying to connect websocket without hardcoding
     */
    public static void setPort(String address) {
    }

    /**
     * ask the user which port they want to connect to,
     * iff their response isn't a number ask again,
     * iff it is a number return the associated address
     *
     * @return a String of the form "http://localhost:[PORT NUMBER]/"
     * where [PORT NUMBER] is a user-specified int
     * */
    public static String getAddress(){
        //Scanner input = new Scanner(System.in);
        System.out.println("On which port is the server?");
        int port =0;
        try{
            Scanner input = new Scanner(System.in);
            port = input.nextInt();
        }

        catch (InputMismatchException e){
            System.out.println("please provide a number");
            return getAddress();
        }

        return "http://localhost:" + port +"/";
    }



    /**
     * setup for stomp session port, occurs after server is set up
     */
    private StompSession session;
    public void setSession(){
        System.out.println("session is set up");
        session = connect("ws"+ SERVER.substring(4) + "/websocket");
    }

    private StompSession connect(String url) {
        var client = new StandardWebSocketClient();
        var stomp = new WebSocketStompClient(client);
        stomp.setMessageConverter(new MappingJackson2MessageConverter());

        try {
            return stomp.connect(url, new StompSessionHandlerAdapter() {}).get();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        }
        throw new IllegalStateException();
    }

    public <T> void registerForMessages(String dest, Class<T> type, Consumer<T> consumer) {
        session.subscribe(dest, new StompFrameHandler() {
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        });
    }


    public void send(String dest, Object o) {
        session.send(dest, o);
    }



    public <T> T get(String path, GenericType<T> responseType) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path(path) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(responseType);
    }
    public List<ListOfCards> getLists(){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/lists") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<ListOfCards>>() {});
    }

    public <T> T add(String path, Object body, GenericType<T> responseType) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path(path)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(body, APPLICATION_JSON), responseType);
    }

}
