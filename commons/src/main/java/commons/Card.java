package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
import javax.persistence.Entity;

@Entity
public class Card implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @Column(nullable = false)
    private String title;

    @ManyToOne
    @JoinColumn(name = "listOfCards_id")
    private ListOfCards listOfCards;

    /**Constructor for Card
     *
     * @param title
     * @param listOfCards
     */
    public Card(String title, ListOfCards listOfCards) {
        this.title = title;
        this.listOfCards = listOfCards;
    }

    /**Empty constructor for Card
     *
     */
    public Card() {

    }

    /**Getter for id
     *
     * @return long
     */
    public long getId() {
        return id;
    }

    /**Getter for title
     *
     * @return long
     */
    public String getTitle() {
        return title;
    }

    /**getter for listOfCards
     *
     * @return ListOfCards
     */
    public ListOfCards getListOfCards() {
        return listOfCards;
    }

    /**Setter for id
     *
     * @param id
     */
    public void setId(long id) {
        this.id = id;
    }

    /**Setter for title
     *
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**Setter for listOfCards
     *
     * @param listOfCards
     */
    public void setListOfCards(ListOfCards listOfCards) {
        this.listOfCards = listOfCards;
    }

    /**Equals method for cards
     *
     * @param o
     * @return true iff they are completely the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (id != card.id) return false;
        if (!title.equals(card.title)) return false;
        return listOfCards.equals(card.listOfCards);
    }

    /**Hashcode function for card
     *
     * @return int
     */
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + listOfCards.hashCode();
        return result;
    }

    /**ToString method for Card
     *
     * @return String
     */
    @Override
    public String toString() {
        return "Card: id =" + id + ", title = " + title;
    }
}