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
    @Column(nullable = false)
    @GeneratedValue
    public long id;

    public String title;

    @ManyToOne(targetEntity = Board.class, cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "board", referencedColumnName = "id")
    public Board board;

//    @OneToMany(mappedBy = "listOfCards", fetch = FetchType.LAZY,
//            cascade = CascadeType.ALL)
//    public List<Card> cards = new ArrayList<>();

    /**
     * Constructor for ListOfCards with title and Board
     *
     * @param title title   
     * @param board reference to Board
     */
    public ListOfCards(String title, Board board) {
        this.title = title;
        this.board = board;
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
        return a.toString();
    }
}
