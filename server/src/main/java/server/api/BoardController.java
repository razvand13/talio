package server.api;

import commons.Board;
import commons.Card;
import commons.ListOfCards;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardRepository repo;

    /**
     * initializes the controller
     * @param repo the repository to use
     */
    public BoardController(BoardRepository repo) {
        this.repo = repo;
    }

    /**
     *
     * @return a list of all boards
     */
    @GetMapping(path = {"", "/"})
    public List<Board> getAllBoards(){
        return repo.findAll();
    }

    /**
     *
     * @param id the id of the board you want
     * @return the board with that id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Board> getBoardById(@PathVariable("id") long id) {
        if(id < 0 || !repo.existsById(id)) {
            return  ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    /**
     * get a ListOfCards by its id and the id of the board in which it's located
     * @param boardId id of the board in where the list is located
     * @param listId id of the list we're looking for
     * @return the list with the provided listId in the board with the provided boardId
     */
    @GetMapping("{boardId}/{listId}")
    public ResponseEntity<ListOfCards> getListById(@PathVariable("boardId") long boardId, @PathVariable("listId") long listId){
        if(listId<0 || boardId<0 || !repo.existsById(boardId)) {
            return ResponseEntity.badRequest().build();
        }
        for(ListOfCards list: repo.getById(boardId).getLists()){
            if(list.getId() == listId){
                return ResponseEntity.ok(list);
            }
        }
        return ResponseEntity.badRequest().build();
    }

    /**
     * get a card by providing the ids of the card and of the board and list it's in.
     * @param boardId the board the card is located in
     * @param listId the list the card is located in
     * @param cardId the card we're looking for
     * @return badRequest iff the card couldn't be found, ok with Card iff it is found.
     */
    @GetMapping("{boardId}/{listId}/{cardId}")
    public ResponseEntity<Card> getCardById(@PathVariable("boardId") long boardId, @PathVariable("listId") long listId, @PathVariable("cardId") long cardId){

        if(cardId<0 || listId<0 || boardId<0 || !repo.existsById(boardId)){
            return ResponseEntity.badRequest().build();
        }

        ListOfCards mylist = null;
        for(ListOfCards list: repo.getById(boardId).getLists()){
            if(list.getId() == listId){
                mylist = list;
            }
        }
        if(mylist == null){
            return ResponseEntity.badRequest().build();
        }


        for(Card card: mylist.getCards()){
            if(card.getId() == cardId){
                return ResponseEntity.ok(card);
            }
        }
        return ResponseEntity.badRequest().build();
    }



    /**
     * adds a board to the repo
     * @param board the board that needs to be added
     * @return badRequest iff the board is null. ok with the provided board iff it was added successfully.
     */
    @PostMapping(path ={"","/"})
    public ResponseEntity<Board> addBoard(@RequestBody Board board) {

        if(board == null){
            return ResponseEntity.badRequest().build();
        }

        //there already exists a board with this id
        if(repo.existsById(board.getId())){
            return ResponseEntity.badRequest().build();
        }
        repo.save(board);
        return ResponseEntity.ok(board);
    }

    /**
     * @param list the list that needs to be added
     * @param id the id of the board it needs to be added to
     * @return badRequest iff list is null or there exists no board with the provided id. ok with the provided list iff it was added successfully
     */
    @PostMapping("/{id}")
    public ResponseEntity<ListOfCards> addList(@RequestBody ListOfCards list, @PathVariable("id") long id) {
        if(list == null){
            return ResponseEntity.badRequest().build();
        }
        if(id<0 || !repo.existsById(id)){
            return  ResponseEntity.badRequest().build();
        }

        repo.findById(id).get().addList(list);
        return ResponseEntity.ok(list);
    }

    /**
     * adds a card to the provided list in the provided board
     * @param card card that gets added
     * @param boardId board it gets added to
     * @param listId list it gets added to
     * @return badrequest iff the provided  board or list couldn't be found. ok with card iff it was added successfully
     */
    @PostMapping("/{boardID}/{listId}")
    public ResponseEntity<Card> addCard(@RequestBody Card card, @PathVariable("boardId") long boardId, @PathVariable("listId") long listId) {
        if (card == null || listId<0 || boardId<0 || !repo.existsById(boardId)){
            return ResponseEntity.badRequest().build();
        }
        for(ListOfCards list: repo.getById(boardId).getLists()){
            if(list.getId() == listId){
                list.addCard(card);
                return ResponseEntity.ok(card);
            }
        }
        return ResponseEntity.badRequest().build();
    }
}
