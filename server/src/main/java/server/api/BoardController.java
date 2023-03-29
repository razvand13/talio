package server.api;

import commons.Board;
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
    public List<Board> getAll(){
        return repo.findAll();
    }

    /**
     *
     * @param id the id of the board you want
     * @return the board with that id
     */
    @GetMapping("/{id}")
    public ResponseEntity<Board> getById(@PathVariable("id") long id) {
        if(id < 0 || !repo.existsById(id)) {
            return  ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(repo.findById(id).get());
    }

    /**
     *
     * @param board the board to be added
     * @return badRequest iff board is null or there already exists a board with the id of the provided board,
     * ok(board) iff ith was added successfully
     */
    @PostMapping(path ={"","/"})
    public ResponseEntity<Board> add(@RequestBody Board board) {

        if(board == null || board.title == null){
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
     *
     * @param id id of the Board to be deleted
     */
    //@Transactional not sure if this is necessary
    public void deleteById(long id){
        repo.deleteById(id);
    }

    /**
     * deletes all boards form the repo
     */
    public void deleteAll(){
        repo.deleteAll();
    }

}
