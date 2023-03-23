package server.api;

import commons.Board;
import commons.ListOfCards;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;
import server.database.ListRepository;

import java.util.List;

@RestController
@RequestMapping("api/lists")
public class ListController {
    private ListRepository listRepo;
    private BoardRepository boardRepo;

    /**
     *
     * @param listRepo the list repository
     * @param boardRepo the board repository used when adding a list to see in which board to add it
     */
    public ListController(ListRepository listRepo, BoardRepository boardRepo) {
        this.listRepo = listRepo;
        this.boardRepo = boardRepo;
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
     * @param boardId the id of the board it needs to be added to
     * @return badRequest iff it couldn't be added, ok with the list if it was added successfully
     */
    @PostMapping
    public ResponseEntity<ListOfCards> add(@RequestBody ListOfCards listOfCards, long boardId){

        if(listOfCards == null){
            return ResponseEntity.badRequest().build();
        }

        //there already exists a list with this id
        if(listRepo.existsById(listOfCards.getId())){
            return ResponseEntity.badRequest().build();
        }


        //check if the provided board exists
        if(boardId<0 || !boardRepo.existsById(boardId)){
            return ResponseEntity.badRequest().build();
        }
        Board boardWithID = boardRepo.getById(boardId);

        boardWithID.addList(listOfCards);
        listRepo.save(listOfCards);
        return ResponseEntity.ok(listOfCards);
    }
}
