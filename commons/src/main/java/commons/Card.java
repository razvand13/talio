package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;


@Entity
@Table(name = "card")
public class Card implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String title;

    @ManyToOne(targetEntity = ListOfCards.class, cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "listOfCards", referencedColumnName = "id")
    public ListOfCards listOfCards;

    /**Constructor for Card
     *
     * @param title title
     * @param listOfCards listOfCards
     */
    public Card(String title, ListOfCards listOfCards) {
        this.title = title;
        this.listOfCards = listOfCards;
    }

    /**
     * Constructor for Card only with title
     * @param title title
     */
    public Card(String title){
        this.title = title;
    }

    /**Empty constructor for Card
     *
     */
    private Card() {

    }

    /**
     * Equals method for cards
     *
     * @param o object to compare
     * @return true iff they are completely the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        return id == card.id && title.equals(card.title);
    }

    /**Hashcode function for card
     *
     * @return int
     */
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        return result;
    }

    /**ToString method for Card
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Card: id = " + id + ", title = " + title;
    }
}