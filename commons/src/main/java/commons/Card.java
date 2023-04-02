package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;


@Entity
@Table(name = "cards")
public class Card implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    @Column(unique = true)
    public long id;


   // @OneToOne(cascade = CascadeType.PERSIST)
    public String title;
    public String description;


    @OneToMany(mappedBy = "card", fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
    public Set<Tag> tags;
    public String color;

//    @ManyToOne(fetch = FetchType.LAZY, optional = false)
//    @JoinColumn(name = "list_id", nullable = false)
    @ManyToOne(targetEntity = ListOfCards.class, cascade = CascadeType.MERGE,
            fetch = FetchType.EAGER)
    @JoinColumn(name = "list", referencedColumnName = "id")
    public ListOfCards list;

    @SuppressWarnings("unused")
    private Card() {

    }

    public Card(String title, String description, String color/*, ListOfCards list*/) {
        this.title = title;
        this.description = description;
        this.color = color;
//        this.list = list;
        this.tags = new HashSet<>();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Card card = (Card) o;

        if (id != card.id) return false;
        if (!title.equals(card.title)) return false;
        if (!description.equals(card.description)) return false;
        if (!tags.equals(card.tags)) return false;
        return color.equals(card.color);
        //return list.equals(card.list);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + title.hashCode();
        result = 31 * result + description.hashCode();
        result = 31 * result + tags.hashCode();
        result = 31 * result + color.hashCode();
//        result = 31 * result + list.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tags=" + tags +
                ", color='" + color + '\'' +
                ", list id=" + list.id +
                '}';
    }

    /**
     * simple getter
     * @return id
     */
    public long getId() {
        return id;
    }
}