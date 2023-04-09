package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "board")
public class Board implements Serializable {

    @Id
    @Column(nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;

    public String title;

    /**
     * Constructor method for a Board with title
     * @param title String value for title
     */
    public Board(String title){
        this.title = title;
    }

    /**
     * Empty constructor method for Board
     */
    public Board(){

    }

    /**
     * Equals method for Board
     * @param o Object we compare this Board to
     * @return whether o is the same Board as this
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Board board = (Board) o;
        return id == board.id && Objects.equals(title, board.title);
    }

    /**
     * Hashcode method for Board
     * @return generated hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, title);
    }


    /**
     * toString method of Board
     * @return String format of object
     */
    @Override
    public String toString() {
        return "Board: " +
                "id=" + id +
                ", title=" + title;
    }

    /** Getter for the id
     * @return the id of type long
     */
    public Long getId() {
        return id;
    }

    /** Setter for the id
     * @param id of type long
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** Getter for the title
     * @return the title of type String
     */
    public String getTitle() {
        return title;
    }

    /** Setter for the title
     * @param title of type String
     */
    public void setTitle(String title) {
        this.title = title;
    }
}
