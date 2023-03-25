package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
//import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;
import javax.persistence.Entity;
//import org.apache.commons.lang3.builder.EqualsBuilder;
//import org.apache.commons.lang3.builder.HashCodeBuilder;
//import org.apache.commons.lang3.builder.ToStringBuilder;
@Entity
@Table(name="lists")
public class ListOfCards implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    public String id;
    public String name;
    @OneToMany(mappedBy = "list", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private List<Card> cards;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    public Board board;

    @SuppressWarnings("unused")
    private ListOfCards() {
        // for object mapper
    }

    /**Constructor for ListOfCards
     *
     * @param name - the anme of the list
     * @param board - the board it is in
     */
    public ListOfCards(String name, Board board) {
        this.name = name;
        this.board = board;
    }

    /**Equals method for ListOfCards
     *
     * @param o - the object to co
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListOfCards that = (ListOfCards) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        if (!cards.equals(that.cards)) return false;
        return board.equals(that.board);
    }

    /**Hashcode for ListOfCards
     *
     * @return - an integer representation of a ListOfCards
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + cards.hashCode();
        result = 31 * result + board.hashCode();
        return result;
    }

    /**Tostring method for ListOfCards
     *
     * @return - a string representation of ListOfCards
     */
    @Override
    public String toString() {
        return "ListOfCards{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cards=" + cards +
                ", board=" + board +
                '}';
    }
}
