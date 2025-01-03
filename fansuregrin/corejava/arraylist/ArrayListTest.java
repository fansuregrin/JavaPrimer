package fansuregrin.corejava.arraylist;
import java.util.ArrayList;
import fansuregrin.corejava.equals.Employee;

public class ArrayListTest {
    public static void main(String[] args) {
        var staff = new ArrayList<Employee>();
        staff.add(new Employee("Carl Cracker", 15000, 2021, 11, 3));
        staff.add(new Employee("Bob Brandson", 16000, 2022, 1, 3));
        staff.add(new Employee("Alice Adams", 17000, 2021, 12, 5));

        for (Employee e : staff) {
            e.raiseSalary(5);
        }

        for (Employee e : staff) {
            System.out.println(e);
        }
    }
}
