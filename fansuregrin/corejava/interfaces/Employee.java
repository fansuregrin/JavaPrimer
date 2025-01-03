package fansuregrin.corejava.interfaces;

import java.time.LocalDate;

public class Employee implements Comparable<Employee> {
    private String name;
    private double salary;
    private LocalDate hireDay;

    public Employee(String name, double salary, int year, int month, int day) {
        this.name = name;
        this.salary = salary;
        this.hireDay = LocalDate.of(year, month, day);
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public LocalDate getHireDay() {
        return hireDay;
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    public String toString() {
        return getClass().getName() + "[name=" + name + 
            ",salary=" + salary + ",hireday=" + hireDay + "]";
    }

    @Override
    public int compareTo(Employee other) {
        return Double.compare(salary, other.salary);
    }
}
