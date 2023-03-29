package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;

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
    public Set<ListOfCards> lists;
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

    /**
     * Hashcode method for the object
     *
     * @return the hash value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title, lists, backgroundColor);
    }

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", backgroundColor='" + backgroundColor + '\'' +
                '}';
    }
}