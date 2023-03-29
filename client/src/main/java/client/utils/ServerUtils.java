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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import org.glassfish.jersey.client.ClientConfig;

import commons.Quote;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

public class ServerUtils {


    private static String SERVER = "http://localhost:8080";
    private static String port = "8080";


    public void getQuotesTheHardWay() throws IOException {
        var url = new URL(SERVER+"api/quotes");
        var is = url.openConnection().getInputStream();
        var br = new BufferedReader(new InputStreamReader(is));
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    }

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
        port = address;
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

    public List<Quote> getQuotes() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Quote>>() {});
    }

    public Quote addQuote(Quote quote) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/quotes") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(quote, APPLICATION_JSON), Quote.class);
    }

    /**
     * trying to connect to the user input port
     * @return
     */
//    private String getPort() {
//        String port = getSERVER();
//        port = port.substring(16);
//        System.out.println(port);
//        return port;
//    }


    private StompSession session;

    /**
     * sets session to connect("ws://localhost:[port]/websocket")
     */
    public void setSession(){
        session = connect("ws"+ SERVER.substring(4) + "websocket");
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

    /**
     *
     * @param dest destination the session needs to subscribe to
     * @param type class that need will be sent
     * @param consumer where the messages will be sent to
     * @param <T> 
     */

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

    /**
     *
     * Sends an object by calling the stompSession send method
     * @param dest destination to send too
     * @param o object that needs to be sent
     */
    public void send(String dest, Object o) {
        session.send(dest, o);
    }

}
