package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;
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
    public long id;
    public String title;
    @OneToMany(mappedBy = "board", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    public List<ListOfCards> lists;
    public String backgroundColor; //Background color

    @SuppressWarnings("unused")
    private Board() {
        // for object mapper
    }

    public Board(String title, String backgroundColor) {
        this.title = title;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (id != board.id) return false;
        if (!title.equals(board.title)) return false;
        if (!lists.equals(board.lists)) return false;
        return backgroundColor.equals(board.backgroundColor);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + lists.hashCode();
        result = 31 * result + backgroundColor.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", lists=" + lists +
                ", backgroundColor='" + backgroundColor + '\'' +
                '}';
    }

    /**
     *
     * @return the id of the board
     */
    public long getId() {
        return id;
    }

    /**
     * adds a ListOfCards to lists
     * @param list the list to add to this board
     */
    public void addList(ListOfCards list){
        if(lists == null){
            lists = new ArrayList<>();
        }
        lists.add((list));
    }

    public List<ListOfCards> getLists() {
        return lists;
    }
}