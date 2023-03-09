package DataStructures;

public class Tag {
    public String id;
    public String nameOfTag;
    public String color;

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