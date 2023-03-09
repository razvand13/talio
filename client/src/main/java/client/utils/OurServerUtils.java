package client.utils;

import static jakarta.ws.rs.core.MediaType.APPLICATION_JSON;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import DataStructures.Card;
import jakarta.ws.rs.core.Response;
import org.glassfish.jersey.client.ClientConfig;

import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.GenericType;

public class OurServerUtils {

    private static final String SERVER = "http://localhost:8080/";

    public List<Card> getCard() {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .get(new GenericType<List<Card>>() {});
    }

    public Card addCard(Card card) {
        return ClientBuilder.newClient(new ClientConfig()) //
                .target(SERVER).path("api/cards") //
                .request(APPLICATION_JSON) //
                .accept(APPLICATION_JSON) //
                .post(Entity.entity(card, APPLICATION_JSON), Card.class);
    }

    private static final ExecutorService EXEC = Executors.newSingleThreadExecutor();

    //dealing with return from client and giving us a quote
    //for long polling
    public void registerForUpdates(Consumer<Card> consumer) {
        //need to execute in a new thread so application is not constantly blocked

        //no error handling -> to be implemented
        EXEC.submit(() -> {
            while(!Thread.interrupted()) {
                var res = ClientBuilder.newClient(new ClientConfig()) //
                        .target(SERVER).path("api/cards/updates") //
                        .request(APPLICATION_JSON) //
                        .accept(APPLICATION_JSON) //
                        .get(Response.class);  //we use response so not everything is automatically a quote

                if(res.getStatus() == 204) { //no content, skip iteration
                    continue;
                }
                var c = res.readEntity(Card.class);
                consumer.accept(c);
            }
        });

    }

    public void stop() {
        EXEC.shutdown();
    }
}