package DataStructures;

import DataStructures.Tag;

import java.util.List;

public class Card {
    public String ID;
    public String Title;
    public String Description;
    public List<String> Subtasks;
    public List<Tag> Tags;
    public String Color;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!ID.equals(card.ID)) return false;
        if (!Title.equals(card.Title)) return false;
        if (!Description.equals(card.Description)) return false;
        if (!Subtasks.equals(card.Subtasks)) return false;
        if (!Tags.equals(card.Tags)) return false;
        return Color.equals(card.Color);
    }

    @Override
    public int hashCode() {
        int result = ID.hashCode();
        result = 31 * result + Title.hashCode();
        result = 31 * result + Description.hashCode();
        result = 31 * result + Subtasks.hashCode();
        result = 31 * result + Tags.hashCode();
        result = 31 * result + Color.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "ID='" + ID + '\'' +
                ", Title='" + Title + '\'' +
                ", Description='" + Description + '\'' +
                ", Subtasks=" + Subtasks +
                ", Tags=" + Tags +
                ", Color='" + Color + '\'' +
                '}';
    }
}