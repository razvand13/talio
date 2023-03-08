package DataStructures;

import java.util.List;

public class MotherBoard {
    public List<Board> Boards;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MotherBoard that = (MotherBoard) o;

        return Boards.equals(that.Boards);
    }

    @Override
    public int hashCode() {
        return Boards.hashCode();
    }

    @Override
    public String toString() {
        return "MotherBoard{" +
                "Boards=" + Boards +
                '}';
    }
}
