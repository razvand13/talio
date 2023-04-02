package server.api;

//import commons.Board;
import commons.Card;
import commons.ListOfCards;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
//import server.database.BoardRepository;
import server.database.CardRepository;
import server.database.ListRepository;

import java.util.List;

@RestController
@RequestMapping("api/lists")
public class ListController {
    private ListRepository listRepo;
    private CardRepository cardRepo;
    //private BoardRepository boardRepo;

    /**
     *
     * @param listRepo the list repository
     * //@param boardRepo the board repository used when adding a list to see in which board to add it
     */
    public ListController(ListRepository listRepo/*, BoardRepository boardRepo*/) {
        this.listRepo = listRepo;
        //    this.boardRepo = boardRepo;
    }

    /**
     *
     * @return all lists in the repository
     */
    @GetMapping(path = {"", "/"})
    public List<ListOfCards> getAll(){
        return listRepo.findAll();
    }

    /**
     *
     * @param id id of the list you're looking for
     * @return badRequest iff it wasn't found, ok with the list iff it was
     */
    @GetMapping("/{id}")
    public ResponseEntity<ListOfCards> getById(@PathVariable("id") long id) {
        if(id < 0 || !listRepo.existsById(id)) {
            return  ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(listRepo.findById(id).get());
    }

    /**
     *
     * @param listOfCards the list that needs to be added
     * //@param boardId the id of the board it needs to be added to
     * @return badRequest iff it couldn't be added, ok with the list if it was added successfully
     */
    @PostMapping
    public ResponseEntity<ListOfCards> add(@RequestBody ListOfCards listOfCards/*, long boardId*/){
        //System.out.println("hahaah");
        //there already exists a list with this id
        if(listRepo.existsById(listOfCards.getId())){
            return ResponseEntity.badRequest().build();
        }

/*
        //check if the provided board exists
        if(boardId<0 || !boardRepo.existsById(boardId)){
            return ResponseEntity.badRequest().build();
        }
        Board boardWithID = boardRepo.getById(boardId);

        boardWithID.addList(listOfCards);

 */
        listOfCards = listRepo.save(listOfCards);
        return ResponseEntity.ok(listOfCards);
    }

    @PostMapping("/cards/{id}")
    public ResponseEntity<Card> addCard(@PathVariable("id") long id, @RequestBody Card card){
        System.out.println("got here");
        if(id<0 || !listRepo.existsById(id)){
            return ResponseEntity.badRequest().build();
        }

        //listRepo.getById(id).addCard(card);
        cardRepo.save(card);
        return ResponseEntity.ok(card);
    }

    @MessageMapping("/lists/cards") //app/quotes -> path for basically any client (consumer)
    @SendTo("/topic/lists/cards")// (producer)
    public Card addMessage(Card card) {
        addCard(card.list.id, card);
        return card;
    }
    @MessageMapping("/lists") //app/quotes -> path for basically any client (consumer)
    @SendTo("/topic/lists")// (producer)
    public ListOfCards addMessage(ListOfCards loc/*, long boardId*/) {
        add(loc/*, boardId*/);
        return loc;
    }
    /**
     *
     * @param id id of the list to be deleted
     */
    //@Transactional not sure if this is necessary
    public void deleteById(long id){
        listRepo.deleteById(id);
    }
    /**
     * deletes all lists form the repo
     */
    public void deleteAll(){
        listRepo.deleteAll();
    }

}
