package server.database;

import commons.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long>{

    /**
     * Find all Cards from the specified ListOfCards, in the order of their position inside the list
     * @param listId ListOfCards id
     * @return a List<Card> containing the query result
     */
    @Query("SELECT c FROM Card c WHERE c.listOfCards.id = :listId ORDER BY c.position")
    List<Card> findAllByListId(long listId);

    /**
     * Delete all cards from a certain list
     * Used to avoid FK constraint errors
     * @param listId list id
     */
    @Query("DELETE FROM Card c WHERE c.listOfCards.id = :listId")
    void deleteAllByListId(long listId);
}
