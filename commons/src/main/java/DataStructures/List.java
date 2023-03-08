package DataStructures;


public class List {
    public String ID;
    public String Name;
    public java.util.List<Card> Cards;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        List list = (List) o;

        if (!ID.equals(list.ID)) return false;
        if (!Name.equals(list.Name)) return false;
        return Cards.equals(list.Cards);
    }

    @Override
    public int hashCode() {
        int result = ID.hashCode();
        result = 31 * result + Name.hashCode();
        result = 31 * result + Cards.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "List{" +
                "ID='" + ID + '\'' +
                ", Name='" + Name + '\'' +
                ", Cards=" + Cards +
                '}';
    }
}