package server.database;

import DataStructures.ListOfCards;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ListOfCardRepository extends JpaRepository<ListOfCards, Long> {
}
