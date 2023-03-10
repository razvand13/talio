package DataStructures;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String id;
    public String title;
    public List<ListOfCards> listOfLists;
    public String backgroundColor; //Background color

    @SuppressWarnings("unused")
    private Board() {
        // for object mapper
    }

    public Board(String title, List<ListOfCards> listOfLists, String backgroundColor) {
        this.title = title;
        this.listOfLists = listOfLists;
        this.backgroundColor = backgroundColor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (!id.equals(board.id)) return false;
        if (!title.equals(board.title)) return false;
        if (!listOfLists.equals(board.listOfLists)) return false;
        return backgroundColor.equals(board.backgroundColor);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + listOfLists.hashCode();
        result = 31 * result + backgroundColor.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", listOfLists=" + listOfLists +
                ", backgroundColor='" + backgroundColor + '\'' +
                '}';
    }
}