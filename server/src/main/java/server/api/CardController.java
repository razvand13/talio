package server.api;

import commons.Card;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.database.CardRepository;
import server.database.ListRepository;

import java.util.List;

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

    /**
     *
     * @param card card that needs to be added
     * @return badRequest if it couldn't be added, ok with the provided card
     * iff it was added successfully
     */
    @PostMapping(path ={"","/"})
    public ResponseEntity<Card> add(@RequestBody Card card) {
        System.out.println("got here");
        if(card == null){
            System.out.println("IS NULL");
            return ResponseEntity.badRequest().build();
        }


        if(!listRepo.existsById(card.listOfCards.id)){
            return ResponseEntity.badRequest().build();
        }

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
        cardRepo.save(card);
        return card;
    }


}
