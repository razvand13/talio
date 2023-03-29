package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
//import static org.apache.commons.lang3.builder.ToStringStyle.MULTI_LINE_STYLE;
import javax.persistence.Entity;
//import org.apache.commons.lang3.builder.EqualsBuilder;
//import org.apache.commons.lang3.builder.HashCodeBuilder;
//import org.apache.commons.lang3.builder.ToStringBuilder;
@Entity
@Table(name="tags")
public class Tag implements Serializable{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String nameOfTag;
    public String color;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "card_id", nullable = false)
    public Card card;
    @SuppressWarnings("unused")
    private Tag() {
        // for object mapper
    }

    public Tag(String nameOfTag, String color, Card card) {
        this.nameOfTag = nameOfTag;
        this.color = color;
        this.card = card;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (id != tag.id) return false;
        if (!nameOfTag.equals(tag.nameOfTag)) return false;
        if (!color.equals(tag.color)) return false;
        return card.equals(tag.card);
    }

    /**
     * Hashcode method for the object
     *
     * @return the hash value
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, nameOfTag, color, card);
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id='" + id + '\'' +
                ", nameOfTag='" + nameOfTag + '\'' +
                ", color='" + color + '\'' +
                ", card=" + card +
                '}';
    }
}