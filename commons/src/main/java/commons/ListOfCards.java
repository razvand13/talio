package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
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
    public long id;
    public String name;
    @OneToMany(mappedBy = "list", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    private Set<Card> cards;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "board_id", nullable = false)
    public Board board;

    @SuppressWarnings("unused")
    private ListOfCards() {
        // for object mapper
    }

    public ListOfCards(String name, Board board) {
        this.name = name;
        this.board = board;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListOfCards that = (ListOfCards) o;

        if (id != that.id) return false;
        if (!name.equals(that.name)) return false;
        if (!cards.equals(that.cards)) return false;
        return board.equals(that.board);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        result = 31 * result + cards.hashCode();
        result = 31 * result + board.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ListOfCards{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cards=" + cards +
                ", board=" + board +
                '}';
    }

    /**
     * simple getter
     * @return id
     */
    public long getId() {
        return id;
    }

    /**
     * simple getter
     * @return cards
     */
    public Set<Card> getCards() {
        return cards;
    }

    /**
     * adds a card to cards
     * @param card card to be added
     */
    public void addCard(Card card){
        cards.add(card);
    }
}