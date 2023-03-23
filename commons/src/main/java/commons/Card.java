package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;
import javax.persistence.Entity;

@Entity
@Table(name = "cards")
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true)
    public long id;


    @OneToOne(cascade = CascadeType.PERSIST)
    public String title;
    public String description;


    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)

    public Set<Tag> tags;
    public String color;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "list_id", nullable = false)
    public ListOfCards list;
    @SuppressWarnings("unused")
    private Card() {
        // for object mapper
    }

    /**Constructor for Card
     *
     * @param title - the title of the card
     * @param description - the description of the card
     * @param color - the color of the card
     * @param list - the list that contains the card
     */
    public Card(String title, String description, String color, ListOfCards list) {
        this.title = title;
        this.description = description;
        this.color = color;
        this.list = list;
    }


    /**Equals method for Card
     *
     * @param o - the object to compare to
     * @return true iff this and o have the same contents
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (id != card.id) return false;
        if (!title.equals(card.title)) return false;
        if (!description.equals(card.description)) return false;
        if (!tags.equals(card.tags)) return false;
        if (!color.equals(card.color)) return false;
        return list.equals(card.list);
    }

    /**Hashcode for card
     *
     * @return an integer representing the hashcode of a card
     */
    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + tags.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + list.hashCode();
        return result;
    }

    /**Tostring method for Card
     *
     * @return - a string representation of Card
     */
    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", color='" + color + '\'' +
                ", list=" + list +
                '}';
    }
}