package server.database;

import commons.ListOfCards;
import org.springframework.data.jpa.repository.JpaRepository;
public interface ListRepository extends JpaRepository<ListOfCards, Long>{
}
