package pr.iceworld.fernando;

public abstract class AbsPerson {
    public AbsPerson() {
    }
    public abstract String getFirstName();

    public abstract String getLastName();

    public abstract String characteristics();

    public String hair() {
        return "black";
    }
}