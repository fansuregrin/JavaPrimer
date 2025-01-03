package fansuregrin.corejava.abstractclass;

public abstract class Person {
    private String name;
    public abstract String getDescription();

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
