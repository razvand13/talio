package DataStructures;

import DataStructures.Tag;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String id;
    public String title;
    public String description;
    public List<String> subtasks;
    public List<Tag> tags;
    public String color;

    @SuppressWarnings("unused")
    private Card() {
        // for object mapper
    }

    public Card(String title, String description, List<String> subtasks, List<Tag> tags, String color) {
        this.title = title;
        this.description = description;
        this.subtasks = subtasks;
        this.tags = tags;
        this.color = color;
    }

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