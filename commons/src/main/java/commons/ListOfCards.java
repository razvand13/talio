package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
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
    private Board board;

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
        return getId() == that.getId() && Objects.equals(name, that.name) && Objects.equals(getCards(), that.getCards()) && Objects.equals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), name, getCards(), board);
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