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
@Table(name="boards")
public class Board implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    public String id;
    public String title;
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    public Set<ListOfCards> lists;
    public String backgroundColor; //Background color

    @SuppressWarnings("unused")
    private Board() {
        // for object mapper
    }

    /**Construcor for Board
     *
     * @param title - the title of the board
     * @param backgroundColor - the background color of the board
     */
    public Board(String title, String backgroundColor) {
        this.title = title;
        this.backgroundColor = backgroundColor;
    }

    /**Equals method for board
     *
     * @param o - the object to compare to
     * @return true iff they are the have the same contents
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (!id.equals(board.id)) return false;
        if (!title.equals(board.title)) return false;
        if (!lists.equals(board.lists)) return false;
        return backgroundColor.equals(board.backgroundColor);
    }

    /**Hashcode method for Board
     *
     * @return - an integer resembling the hashcode
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + lists.hashCode();
        result = 31 * result + backgroundColor.hashCode();
        return result;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", lists=" + lists +
                ", backgroundColor='" + backgroundColor + '\'' +
                '}';
    }
}