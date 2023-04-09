package server.api;

import commons.Card;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.async.DeferredResult;
import server.database.CardRepository;
import server.database.ListRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

@RestController
@RequestMapping("api/cards")
public class CardController {

    private final CardRepository cardRepo;
    private final ListRepository listRepo;

    /**
     * constructor for the controller
     * @param cardRepo the CardRepository
     * @param listRepo the ListRepository used when adding a card to see in which list to add it
     */
    public CardController(CardRepository cardRepo, ListRepository listRepo) {
        this.cardRepo = cardRepo;
        this.listRepo =listRepo;
    }

    /**
     *
     * @return all cards in the repository
     */
    @GetMapping(path = {"", "/"})
    public List<Card> getAll(){
        return cardRepo.findAll();
    }

    /**
     *
     * @param id id of the card you're looking for
     * @return badRequest if it doesn't exist, ok with the card iff it was found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Card> getById(@PathVariable("id") long id) {
        if(id < 0 || !cardRepo.existsById(id)) {
            return  ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(cardRepo.findById(id).get());
    }

    private Map<Object, Consumer<Card>> listeners = new HashMap<>();

    /**
     * Method for defining results of long polling updates
     * @return DeferredResult<ResponseEntity<Card>>
     */
    @GetMapping("/updates")
    public DeferredResult<ResponseEntity<Card>> getUpdates() {
        var noContent = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        var res = new DeferredResult<ResponseEntity<Card>>(5000L, noContent);

        var key = new Object();
        listeners.put(key, c -> res.setResult(ResponseEntity.ok(c)));

        res.onCompletion(() -> listeners.remove(key));

        return res;
    }

    /**
     * Find all Cards from the specified ListOfCards
     * @param listId ListOfCards id
     * @return a List<Card> containing the query result
     */
    @GetMapping("/list/{listId}")
    public ResponseEntity<List<Card>> getAllByListId(@PathVariable("listId") long listId){
        if(listId < 0 || !listRepo.existsById(listId)){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(cardRepo.findAllByListId(listId));
    }

    /**
     *
     * @param card card that needs to be added
     * @return badRequest if it couldn't be added, ok with the provided card
     * iff it was added successfully
     */
    @PostMapping(path ={"","/"})
    public ResponseEntity<Card> add(@RequestBody Card card) {
        if(card == null){
            return ResponseEntity.badRequest().build();
        }

        if(!listRepo.existsById(card.listOfCards.id)){
            return ResponseEntity.badRequest().build();
        }


        Card finalCard = card; // the IDE needs the card to be effectively final
        listeners.forEach((k, l) -> l.accept(finalCard));

//        card.listOfCards.addCard(card);
        card = cardRepo.save(card);
        System.out.println("Saved card " + card);
        return ResponseEntity.ok(card);
    }

    /**
     *
     * @param id id of the card to be deleted
     */
    //@Transactional not sure if this is necessary
    public void deleteById(long id){
        cardRepo.deleteById(id);
    }

    /**
     * Delete all cards from a certain list
     * Used to avoid FK constraint errors
     * @param listId list id
     */
    @PostMapping("/remove-cards/list/{listId}")
    public void deleteAllFromList(@PathVariable("listId") long listId){
        cardRepo.deleteAllByListId(listId);
    }

    /**
     * deletes all cards form the repo
     */
    public void deleteAll(){
        cardRepo.deleteAll();
    }

    /**
     *
     * @param c card
     * @return Card
     */
    @MessageMapping("/cards") //app/cards -> path for basically any client (consumer)
    @SendTo("/topic/cards")// (producer)
    public Card addMessage(Card c) {
        System.out.println("ADD MESSAGE");
        add(c);
        return c;
    }

    /**
     * Remove card from database
     * @param card Card to be deleted
     * @return deleted Card
     */
    @MessageMapping("/remove-card")
    @SendTo("/topic/remove-card")
    public Card removeCard(Card card){
        cardRepo.deleteById(card.id);
        return card;
    }

    /**
     * Edit card in database
     * @param card Card with changed values
     * @return edited Card
     */
    @MessageMapping("/edit-card")
    @SendTo("/topic/edit-card")
    public Card editCard(Card card){
        if(card.position<0){
            System.out.println("problems" + card);
        }
        cardRepo.save(card);
        return card;
    }


}
