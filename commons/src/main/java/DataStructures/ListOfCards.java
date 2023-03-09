package DataStructures;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public class ListOfCards {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String id;
    public String name;
    public List<Card> cards;

    @SuppressWarnings("unused")
    private ListOfCards() {
        // for object mapper
    }

    public ListOfCards(String name, List<Card> cards) {
        this.name = name;
        this.cards = cards;
    }

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