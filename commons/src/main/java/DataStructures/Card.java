package DataStructures;

import DataStructures.Tag;

import java.util.List;

public class Card {
    public String id;
    public String title;
    public String description;
    public List<String> subtasks;
    public List<Tag> tags;
    public String color;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (!id.equals(card.id)) return false;
        if (!title.equals(card.title)) return false;
        if (!description.equals(card.description)) return false;
        if (!subtasks.equals(card.subtasks)) return false;
        if (!tags.equals(card.tags)) return false;
        return color.equals(card.color);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + subtasks.hashCode();
        result = 31 * result + tags.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", subtasks=" + subtasks +
                ", tags=" + tags +
                ", color='" + color + '\'' +
                '}';
    }
}