package DataStructures;

import java.util.List;

public class ListOfCards {
    public String id;
    public String name;
    public List<Card> cards;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ListOfCards that = (ListOfCards) o;

        if (!id.equals(that.id)) return false;
        if (!name.equals(that.name)) return false;
        return cards.equals(that.cards);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        result = 31 * result + cards.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ListOfCards{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", cards=" + cards +
                '}';
    }
}