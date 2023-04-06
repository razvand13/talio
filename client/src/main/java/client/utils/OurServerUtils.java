package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import commons.Card;
import commons.ListOfCards;
import jakarta.ws.rs.core.Response;
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
     * Set the SERVER variable to the input value
     * @param address address to connect to
     */
    public static void setSERVER(String address){
        SERVER = address;
    }

    /**
     * setup for stomp session port, occurs after server is set up
     */
    private StompSession session;

    /**
     * Sets the current session
     */
    public void setSession(){
        System.out.println("session is set up");
        System.out.println("ws"+ SERVER.substring(4) + "/websocket");
        session = connect("ws"+ SERVER.substring(4) + "/websocket");
    }

    /**
     *
     * @param url url to connect to
     * @return new StompSession
     */
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
     * @param dest
     * @param type
     * @param consumer
     * @param <T>
     */
    public <T> void registerForMessages(String dest, Class<T> type, Consumer<T> consumer) {
        session.subscribe(dest, new StompFrameHandler() {
            /**
             *
             * @param headers the headers of a message
             * @return
             */
            @Override
            public Type getPayloadType(StompHeaders headers) {
                return type;
            }

            /**
             *
             * @param headers the headers of the frame
             * @param payload the payload, or {@code null} if there was no payload
             */
            @Override
            public void handleFrame(StompHeaders headers, Object payload) {
                consumer.accept((T) payload);
            }
        });
    }

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();

    /**
     * Generic long polling update method
          * @param dest URL
          * @param type class
     * @param consumer callback
         * @param <T> generic
     */
    public <T> void registerForUpdates(String dest, Class<T> type, Consumer<T> consumer){

        EXEC.submit(() ->{
            while(!Thread.interrupted()){
                var res = ClientBuilder.newClient(new ClientConfig())
                        .target(SERVER).path(dest)
                        .request(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .get(Response.class);

                if(res.getStatus() == 204) {
                    continue;
                }
                var t = res.readEntity(type);
                consumer.accept(t);
            }

        });

    }

    /**
     * Method that stops the program, including EXEC's thread
     */
    public void stop(){
        EXEC.shutdownNow();
    }

    /**
     *
     * @param dest
     * @param o
     */
    public void send(String dest, Object o) {
        session.send(dest, o);
    }


    /**
     *
     * @param path
     * @param responseType
     * @return invocation response
     * @param <T>
     */
    public <T> T get(String path, GenericType<T> responseType) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path(path) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(responseType);
    }

    /**
     *
     * @return a list of all lists
     */
    public List<ListOfCards> getLists(){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/lists") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<ListOfCards>>() {});
    }

    /**
     * Method for retrieving all cards from the database
     * @return a List<Card> containing all cards stored in the database
     */
    public List<Card> getCards(){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Card>>() {});
    }

    /**
     * Find all Cards from the specified ListOfCards
     * @param listId ListOfCards id
     * @return a List<Card> containing the query result
     */
    public List<Card> getCardsByListId(long listId){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards/list/"+listId) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Card>>() {});
    }

    /**
     *
     * @param path path
     * @param body body
     * @param responseType generic response type
     * @param <T> generic T
     * @return invocation response
     *
     */
    public <T> T add(String path, Object body, GenericType<T> responseType) {
        return ClientBuilder.newClient(new ClientConfig())
                .target(SERVER).path(path)
                .request(APPLICATION_JSON)
                .accept(APPLICATION_JSON)
                .post(Entity.entity(body, APPLICATION_JSON), responseType);
    }

}
