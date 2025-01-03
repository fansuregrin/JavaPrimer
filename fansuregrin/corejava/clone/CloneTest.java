package fansuregrin.corejava.clone;

public class CloneTest {
    public static void main(String[] args) throws CloneNotSupportedException {
        var orig = new Employee("Jack Mon", 14000);
        orig.setHireDay(2011, 9, 20);
        Employee copy = orig.clone();
        copy.raiseSalary(5);
        copy.setHireDay(2022, 3, 11);
        System.out.println("orignal: " + orig);
        System.out.println("copy: " + copy);
    }
}
