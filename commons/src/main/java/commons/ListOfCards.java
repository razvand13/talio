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
    private long id;

    @OneToMany(mappedBy = "listOfCards")
    private List<Card> cards;
    
    private String title;

    /**Constructor for ListOfCards
     *
     * @param title
     * @param cards
     */
    public ListOfCards(String title, List<Card> cards) {
        this.title = title;
        this.cards = cards;
    }

    /**Getter for title
     *
     * @return String
     */
    public String getTitle() {
        return title;
    }

    /**Setter for title
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**Empty constructor for ListOfCards
     *
     */
    public ListOfCards() {
        
    }

    /**Getter for id
     *
     * @return long
     */
    public long getId() {
        return id;
    }

    /**Getter for cards
     *
     * @return List<Card>
     */
    public List<Card> getCards() {
        return cards;
    }

    /**Setter for id
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**Setter for List<Card>
     *
     * @param cards
     */
    public void setCards(List<Card> cards) {
        this.cards = cards;
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