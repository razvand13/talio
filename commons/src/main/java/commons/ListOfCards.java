package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
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
        this.cards = new ArrayList();
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
        return cards.equals(that.cards);
    }

    /**Hashcode method for ListOfCards
     *
     * @return int
     */
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + cards.hashCode();
        return result;
    }

    /**ToString method for ListOfCards
     *
     * @return
     */
    @Override
    public String toString() {
        String a = "ListOfCards: id =" + id + ", title "+ title + ", cards =";
        for(Card i:cards){
            a = a + i.toString() + "\n";
        }
        return a;
    }
}
