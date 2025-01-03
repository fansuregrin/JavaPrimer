package fansuregrin.corejava.interfaces;

import java.util.Arrays;

public class EmployeeSortTest {
    public static void main(String[] args) {
        var staff = new Employee[3];
        staff[0] = new Employee("Harry Hacker", 35000, 2011, 3, 2);
        staff[1] = new Employee("Carl Cracker", 24000, 2014, 11, 4);
        staff[2] = new Employee("Tony Tester", 25000, 2015, 7, 8);

        Arrays.sort(staff);

        for (var e: staff) {
            System.out.println(e);
        }
    }
}
