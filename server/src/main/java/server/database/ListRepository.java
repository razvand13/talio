package server.database;

import commons.ListOfCards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ListRepository extends JpaRepository<ListOfCards, Long>{

    /**
     * Find all lists from the specified board, in the order of their position inside the list
     * @param boardId  board id
     * @return a board containing the query result
     */
    @Query("SELECT l FROM ListOfCards l WHERE l.board.id = :boardId ")
    List<ListOfCards> findByBoardId(long boardId);

}
