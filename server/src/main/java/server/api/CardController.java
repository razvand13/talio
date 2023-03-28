package server.api;

import commons.Card;
import commons.ListOfCards;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
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
     * @param listId id of the list it needs to be added to
     * @return badRequest if it couldn't be added, ok with the provided card iff it was added successfully
     */
    @PostMapping(path ={"","/"})
    public ResponseEntity<Card> add(@RequestBody Card card, long listId) {

        if(card == null){
            return ResponseEntity.badRequest().build();
        }

        //there already exists a card with this id
        if(cardRepo.existsById(card.getId())){
            return ResponseEntity.badRequest().build();
        }

        //check if the provided list exists
        if(listId<0 || !listRepo.existsById(listId)){
            return ResponseEntity.badRequest().build();
        }
        ListOfCards listWithId = listRepo.getById(listId);

        listWithId.addCard(card);
        cardRepo.save(card);
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
}
