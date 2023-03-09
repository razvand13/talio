package DataStructures;

import java.util.List;

public class MotherBoard {
    public List<Board> boards;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MotherBoard that = (MotherBoard) o;

        return boards.equals(that.boards);
    }

    @Override
    public int hashCode() {
        return boards.hashCode();
    }

    @Override
    public String toString() {
        return "MotherBoard{" +
                "boards=" + boards +
                '}';
    }
}