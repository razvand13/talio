package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.lang.reflect.Type;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import commons.Board;
import commons.Card;
import commons.ListOfCards;
import jakarta.ws.rs.BadRequestException;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
public class NewServerUtils {

    private static String SERVER = "http://localhost:8080";


    /**
     * @param address address to connect to
     * Set the SERVER variable to the input value
     */
    public static void setSERVER(String address){
        SERVER =address;
    }
    /**
     * setup for stomp session port, occurs after server is set up
     */
    private StompSession session;
    public void setSession(){
        System.out.println("session is set up");
        session = connect("ws"+ SERVER.substring(4) + "/websocket");
    }

    /**
     * connection to websocket using stomp session
     * sets up Jackson mapper (message converter)
     * @param url set in setSession() ws://localhost:<port>/websocket
     * @return stompsession
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
     * Generic websocket update method
     * @param dest URL
     * @param type class
     * @param consumer callback
     * @param <T> generic
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
     * sends object to server using stompsession
     * @param dest hesitation to send object to
     * @param o object to send
     */
    public void send(String dest, Object o) {
        session.send(dest, o);
    }



    /**
     * Method that stops the program, including EXEC's thread
     */
    public void stop(){
        EXEC.shutdownNow();
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
     * Gets a board from the database by specifying its id
     * @param id id of board
     * @return the board with the id or null if the board wasn't found
     */
    public Board getBoardById(long id){
        try {
            return ClientBuilder.newClient(new ClientConfig()) //
                    .target(SERVER).path("api/boards/" + id) //
                    .request(APPLICATION_JSON) //
                    .accept(APPLICATION_JSON) //
                    .get(new GenericType<Board>() {
                    });
        }
        //board not in db
        catch (BadRequestException e){
            return null;
        }
    }
    /**
     * Gets a list from the database by specifying its id
     * @param id id of list
     * @return the list with the id
     */
    public ListOfCards getListById(long id){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/lists/"+id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<ListOfCards>() {});
    }

    /**
     * Gets a list from the database by specifying the board ID associated with the list
     * @param id id of board
     * @return the list
     */
    public List<ListOfCards> getListByBoardId(long id){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/lists/board/"+ id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<ListOfCards>>() {});
    }

    /**
     * Method for retrieving a card from the database by its ID
     * @param id Card id
     * @return Card
     */
    public Card getCardById(long id){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards/" + id) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Card>() {});
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
     * Find Card from the specified ListOfCards
     * @param listId ListOfCards id
     * @return a Card containing the query result
     */
    public Card getCardByListId(long listId){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards/list/"+listId) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Card>() {});
    }

    /**
     * Delete all cards from a certain list
     * Used to avoid FK constraint errors
     * @param listId list id
     * @return Response
     */
    public Response removeCardsByListId(long listId){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("/remove-cards/list/"+listId) //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .delete();
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



    /**
     * Gets the address of the current server
     * @return SERVER minus the "http://" at the start and the "/" at the end (so localhost:8080 by default)
     */
    public String getAddress(){
        return SERVER.substring(7, SERVER.length()-1);
    }


    /**
     * Gets all the boards from the server
     * @return list of all the boards
     */
    public List<Board> getBoards(){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/boards") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Board>>() {});
    }

    /**
     *
     * @return the most recently added board.
     */
    public Board getMostRecentBoard(){
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/boards/mostRecent") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<Board>() {});
    }
}
