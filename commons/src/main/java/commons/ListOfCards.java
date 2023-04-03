package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import javax.persistence.Entity;
@Entity
public class ListOfCards implements Serializable {

    @Id
    @GeneratedValue
    public long id;

    @OneToMany(mappedBy = "listOfCards")
    public List<Card> cards;

    public String title;

    /**Constructor for ListOfCards
     *
     * @param title
     * @param cards
     */
    public ListOfCards(String title, List<Card> cards) {
        this.title = title;
        this.cards = cards;
    }

    /**Constructor only with title for ListOfCards
     *
     * @param title
     */
    public ListOfCards(String title) {
        this.title = title;
        this.cards = new ArrayList<>();
    }

    /**Empty constructor for ListOfCards
     *
     */
    public ListOfCards() {

    }

    /**Equals method for ListOfCards
     *
     * @param o
     * @return true iff they are completely the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListOfCards that = (ListOfCards) o;

        if (id != that.id) return false;
        if (!Objects.equals(cards, that.cards)) return false;
        return Objects.equals(title, that.title);
    }


    /**
     * Hashcode method for a ListOfCards
     *
     * @return the hash value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }

    /**ToString method for ListOfCards
     *
     * @return human-readable version of the object
     */
    @Override
    public String toString() {
        StringBuilder a = new StringBuilder("ListOfCards: id = " +
                id + ", title = " + title + ", cards =");
        if(cards == null) {
            a.append("N/A;");
            return a.toString();
        }

        for(Card i:cards){
            a.append("\n")
                    .append(i.toString())
                    .append(";");
        }
        return a.toString();
    }

    /**
     * adds a card to cards
     * @param card card to be added
     */
    public void addCard(Card card){
        cards.add(card);
    }
}