package server.api;

import commons.ListOfCards;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;
import server.database.ListRepository;

import java.util.List;

@RestController
@RequestMapping("api/lists")
public class ListController {
    private ListRepository listRepo;
    private final BoardRepository boardRepository;

    /**
     * @param boardRepository board repository
     * @param listRepo the list repository
     */
    public ListController(ListRepository listRepo,
                          BoardRepository boardRepository) {
        this.listRepo = listRepo;
        this.boardRepository = boardRepository;
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
     * Find all Cards from the specified ListOfCards
     * @param  boardId id of board
     * @return a ListOfCards containing the query result
     */
    @GetMapping("/board/{boardId}")
    public ResponseEntity<List<ListOfCards>> getByBoardId(@PathVariable("boardId") long boardId){
        if(boardId < 0 || !boardRepository.existsById(boardId)){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(listRepo.findByBoardId(boardId));
    }


    /**
     *
     * @param listOfCards the list that needs to be added
     * //@param boardId the id of the board it needs to be added to
     * @return badRequest iff it couldn't be added, ok with the list if it was added successfully
     */
    @PostMapping
    public ResponseEntity<ListOfCards> add(@RequestBody ListOfCards listOfCards){
        if(listOfCards == null || listRepo.existsById(listOfCards.id)){
            return ResponseEntity.badRequest().build();
        }
        listOfCards = listRepo.save(listOfCards);
        return ResponseEntity.ok(listOfCards);
    }

    /**
    *
    *@param loc list to be added 
    *@return loc
    */

    @MessageMapping("/lists") //app/quotes -> path for basically any client (consumer)
    @SendTo("/topic/lists")// (producer)
    public ListOfCards addMessage(ListOfCards loc) {
        add(loc);
        return loc;
    }

    /**
     * edit list
     * @param loc list of cards
     * @return list of cards
     */
    @MessageMapping("/edit-lists") //app/quotes -> path for basically any client (consumer)
    @SendTo("/topic/edit-lists")// (producer)
    public ListOfCards editList(ListOfCards loc) {
        listRepo.save(loc);
        return loc;
    }

    /**
     * delete list of cards from database
     * @param loc list of cards to be deleted
     * @return list of cards
     */
    @MessageMapping("/remove-lists") //app/quotes -> path for basically any client (consumer)
    @SendTo("/topic/remove-lists")// (producer)
    public ListOfCards removeList(ListOfCards loc) {
        listRepo.deleteById(loc.id);
        return loc;
    }

    /**
     *
     * @param id id of the list to be deleted
     */
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
