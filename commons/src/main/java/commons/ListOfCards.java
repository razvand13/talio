package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
import javax.persistence.Entity;

@Entity
@Table(name="listOfCards")
public class ListOfCards implements Serializable {

    @Id
    @GeneratedValue
    public long id;

    public String title;

//    @OneToMany(mappedBy = "listOfCards", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    public List<Card> cards = new ArrayList<>();

    /**
     * Constructor for ListOfCards
     *
     * @param title title
     * @param cards cards
     */
    public ListOfCards(String title, List<Card> cards) {
        this.title = title;
//        this.cards = cards;
    }

    /**
     * Constructor only with title for ListOfCards
     *
     * @param title title
     */
    public ListOfCards(String title) {
        this.title = title;
    }

    /**
     * Empty constructor for ListOfCards
     */
    private ListOfCards() {

    }

    /**Equals method for ListOfCards
     *
     * @param o object to compare to
     * @return true iff they are completely the same
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListOfCards that = (ListOfCards) o;
        return id == that.id && Objects.equals(title, that.title);
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
//        if(cards == null) {
//            a.append("N/A;");
//            return a.toString();
//        }
//
//        for(Card i:cards){
//            a.append("\n")
//                    .append(i.toString())
//                    .append(";");
//        }
        return a.toString();
    }
//
//    /**
//     * adds a card to cards
//     * @param card card to be added
//     */
//    public void addCard(Card card){
//        cards.add(card);
//    }
}
