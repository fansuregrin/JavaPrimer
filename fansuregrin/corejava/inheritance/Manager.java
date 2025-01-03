package fansuregrin.corejava.inheritance;

import java.util.Objects;

public class Manager extends Employee {
    private double bonus;

    public Manager(String name, double salary, int year, int month, int day) {
        super(name, salary, year, month, day);
        bonus = 0;
    }

    public double getSalary() {
        return super.getSalary() + bonus;
    }

    public void setBonus(double bonus) {
        this.bonus = bonus;
    }

    public boolean equals(Object obj) {
        if (!super.equals(obj)) return false;
        Manager other = (Manager) obj;
        return bonus == other.bonus;
    }

    public int hashCode() {
        return Objects.hash(super.hashCode(), bonus);
    }

    public String toString() {
        return super.toString() + "[bonus=" + bonus + "]";
    }
}
