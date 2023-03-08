package DataStructures;

import java.util.List;

public class Board {
    public String ID;
    public String Title;
    public List<List> Lists;
    public String BgColor; //Background color

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        if (!ID.equals(board.ID)) return false;
        if (!Title.equals(board.Title)) return false;
        if (!Lists.equals(board.Lists)) return false;
        return BgColor.equals(board.BgColor);
    }

    @Override
    public int hashCode() {
        int result = ID.hashCode();
        result = 31 * result + Title.hashCode();
        result = 31 * result + Lists.hashCode();
        result = 31 * result + BgColor.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Board{" +
                "ID='" + ID + '\'' +
                ", Title='" + Title + '\'' +
                ", Lists=" + Lists +
                ", BgColor='" + BgColor + '\'' +
                '}';
    }
}