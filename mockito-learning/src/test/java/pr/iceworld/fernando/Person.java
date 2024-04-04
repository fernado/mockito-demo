package pr.iceworld.fernando;

public class Person extends AbsPerson {

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        LastName = lastName;
    }

    public Person() {
    }

    private String firstName;
    private String LastName = "Shui";
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public String characteristics() {
        return "Normal";
    }

    public String getIdCard() {
        return "";
    }
}