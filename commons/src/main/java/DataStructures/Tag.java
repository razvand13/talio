package DataStructures;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public String id;
    public String nameOfTag;
    public String color;

    @SuppressWarnings("unused")
    private Tag() {
        // for object mapper
    }

    public Tag(String nameOfTag, String color) {
        this.nameOfTag = nameOfTag;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (!id.equals(tag.id)) return false;
        if (!nameOfTag.equals(tag.nameOfTag)) return false;
        return color.equals(tag.color);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + nameOfTag.hashCode();
        result = 31 * result + color.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id='" + id + '\'' +
                ", nameOfTag='" + nameOfTag + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}