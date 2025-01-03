package fansuregrin.corejava.clone;

import java.util.Date;
import java.util.GregorianCalendar;

public class Employee implements Cloneable {
    private String name;
    private double salary;
    private Date hireDay;

    public Employee(String name, double salary) {
        this.name = name;
        this.salary = salary;
        this.hireDay = new Date();
    }

    public String getName() {
        return name;
    }

    public double getSalary() {
        return salary;
    }

    public void raiseSalary(double byPercent) {
        double raise = salary * byPercent / 100;
        salary += raise;
    }

    public void setHireDay(int year, int month, int day) {
        Date newHireDay = new GregorianCalendar(year, month-1, day).getTime();
        hireDay.setTime(newHireDay.getTime());
    }

    public Employee clone() throws CloneNotSupportedException {
        Employee e = (Employee) super.clone();
        e.hireDay = (Date) hireDay.clone();
        return e;
    }

    public String toString() {
        return getClass().getName() + "[name=" + name + 
            ",salary=" + salary + ",hireday=" + hireDay + "]";
    }
}
