package DataStructures;

import java.util.List;

public class Board {
    public String id;
    public String title;
    public List<List> listOfLists;
    public String backgroundColor; //Background color

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