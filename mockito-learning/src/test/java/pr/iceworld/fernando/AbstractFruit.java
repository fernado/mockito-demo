package pr.iceworld.fernando;

public abstract class AbstractFruit {
    private String name;
    private int size;

    public AbstractFruit(String name, int size) {
        this.name = name;
        this.size = size;
    }
    public AbstractFruit() {
    }
    public abstract double cost();
}