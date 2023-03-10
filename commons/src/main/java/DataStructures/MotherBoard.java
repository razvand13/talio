package DataStructures;

import java.util.List;

public class MotherBoard {
    public List<Board> boards;

    @SuppressWarnings("unused")
    private MotherBoard() {
        // for object mapper
    }

    public MotherBoard(List<Board> boards) {
        this.boards = boards;
    }

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