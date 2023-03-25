package commons;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.*;
import java.io.Serializable;
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
    public String id;
    public String nameOfTag;
    public String color;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "card_id", nullable = false)
    public Card card;
    @SuppressWarnings("unused")
    private Tag() {
        // for object mapper
    }

    /**Constructor for Tag
     *
     * @param nameOfTag - name
     * @param color - color
     * @param card - the respective card
     */
    public Tag(String nameOfTag, String color, Card card) {
        this.nameOfTag = nameOfTag;
        this.color = color;
        this.card = card;
    }

    /**Equals method for Tag
     *
     * @param o the object to compare it to
     * @return true iff they contain the same thing
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (!id.equals(tag.id)) return false;
        if (!nameOfTag.equals(tag.nameOfTag)) return false;
        if (!color.equals(tag.color)) return false;
        return card.equals(tag.card);
    }

    /**Hashcode method for Tag
     *
     * @return  an int
     */
    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nameOfTag.hashCode();
        result = 31 * result + color.hashCode();
        result = 31 * result + card.hashCode();
        return result;
    }

    /**Tostring method for Tag
     *
     * @return a string representation of Tag
     */
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
