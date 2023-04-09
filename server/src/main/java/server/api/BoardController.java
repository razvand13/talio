package server.api;

import commons.Board;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.*;
import server.database.BoardRepository;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    private final BoardRepository repo;
    private Board mostRecent;
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
     * adds a board to the repo
     * @param board the board that needs to be added
     * @return badRequest iff the board is null. ok with the provided board
     *          iff it was added successfully.
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
        mostRecent = board;
        return ResponseEntity.ok(board);
    }

    /**
     * gives the most recently added board
     * @return most recently added board
     */
    @GetMapping("/mostRecent")
    public Board getMostRecent() {
        return mostRecent;
    }

    @MessageMapping("/boards") //app/quotes -> path for basically any client (consumer)
    @SendTo("/topic/boards")// (producer)
    public Board addMessage(Board board) {
        addBoard(board);
        return board;
    }

    @MessageMapping("/remove-board")
    @SendTo("/topic/remove-board")
    public Board removeBoard(Board board){
        repo.deleteById(board.id);
        return board;
    }

    public void deleteById(long id){
        repo.deleteById(id);
    }
}
