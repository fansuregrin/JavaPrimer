package fansuregrin.corejava.gp;

import java.time.LocalDate;
import fansuregrin.corejava.inheritance.Manager;
import fansuregrin.corejava.inheritance.Employee;

public class GPTest {
    @SuppressWarnings("unused")
    public static void main(String[] args) {
        String[] words = {"Larry", "has", "a", "little", "lamb"};
        Pair<String> mm = ArrayAlg.minmax(words);
        System.out.println("min = " + mm.getFirst());
        System.out.println("max = " + mm.getSecond());

        System.out.println("middle = " + ArrayAlg.getMiddle(words));

        // use generic version of `minmax` method
        mm = ArrayAlg.minmax2(words);
        System.out.println("min = " + mm.getFirst());
        System.out.println("max = " + mm.getSecond());
        
        LocalDate[] birthdays = {
            LocalDate.of(2001, 3, 22),
            LocalDate.of(2001, 5, 21),
            LocalDate.of(2000, 4, 12),
            LocalDate.of(1999, 1, 2)
        };
        Pair<LocalDate> mm2 = ArrayAlg.minmax2(birthdays);
        System.out.println("min = " + mm2.getFirst());
        System.out.println("max = " + mm2.getSecond());

        // Pair<int> a = new Pair<>();
        Pair<Integer> a = new Pair<>();
        
        // if (mm instanceof Pair<T>) {}
        if (mm instanceof Pair<String>) {}
        if (a.getClass() == mm.getClass()) {
            System.out.println("always equal");
        };
        
        // Pair<String>[] a = new Pair<>[10];

        // Manager 和 Employee 由继承关系，但是 Pair<Manager> 没有 Pair<Employee> 没有关系
        Manager ceo = new Manager("Mike Albert", 80000, 2010, 10, 10);
        Manager cfo = new Manager("Lux Liu", 80000, 2010, 10, 10);
        Pair<Manager> managerBuddies = new Pair<>(ceo, cfo);
        // Type mismatch: cannot convert from Pair<Manager> to Pair<Employee>
        // Pair<Employee> employeeBuddies = managerBuddies; // error
        // 数组会记录类型
        Manager[] managerBuddies2 = {ceo, cfo};
        Employee[] employeeBuddies2 = managerBuddies2; // ok
        Employee lowlyEmployee = new Employee("Alan Cracker", 14000, 2014, 6, 10);
        // throw java.lang.ArrayStoreException
        // employeeBuddies2[0] = lowlyEmployee;

        // 通配符类型 (wildcard type)
        printBuddies(managerBuddies);
        Pair<? extends Employee> employeeBuddies3 = managerBuddies; // ok
        // employeeBuddies3.setFirst(lowlyEmployee); // error
    }

    public static void printBuddies(Pair<? extends Employee> p) {
        Employee first = p.getFirst();
        Employee second = p.getSecond();
        System.out.println(first.getName() + " and " + second.getName()
            + " are buddies.");
    }

    public static void minmaxBonus(Manager[] managers, Pair<? super Manager> result) {
        if (managers == null || managers.length == 0) return;
        Manager min = managers[0];
        Manager max = managers[0];
        for (int i=1; i<managers.length; ++i) {
            if (managers[i].getBonus() < min.getBonus()) min = managers[i];
            if (managers[i].getBonus() > max.getBonus()) max = managers[i]; 
        }
        result.setFirst(min);
        result.setSecond(max);
    }
}