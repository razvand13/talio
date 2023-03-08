package DataStructures;

public class Tag {
    public String ID;
    public String NameOfTag;
    public String Color;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Tag tag = (Tag) o;

        if (!ID.equals(tag.ID)) return false;
        if (!NameOfTag.equals(tag.NameOfTag)) return false;
        return Color.equals(tag.Color);
    }

    @Override
    public int hashCode() {
        int result = ID.hashCode();
        result = 31 * result + NameOfTag.hashCode();
        result = 31 * result + Color.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "ID='" + ID + '\'' +
                ", NameOfTag='" + NameOfTag + '\'' +
                ", Color='" + Color + '\'' +
                '}';
    }
}