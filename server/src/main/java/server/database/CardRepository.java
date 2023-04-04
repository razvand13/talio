package server.database;

import commons.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long>{

    @Query("SELECT c FROM Card c WHERE c.listOfCards.id = :listId") // todo ORDER BY position
    List<Card> findAllByListId(long listId);
}
