package fansuregrin.corejava.inheritance;

public class ManagerTest {
    public static void main(String[] args) {
        var boss = new Manager("San Zhang", 51000, 2009, 12, 3);
        boss.setBonus(6000);

        var staff = new Employee[3];
        staff[0] = boss;
        staff[1] = new Employee("Ming Li", 23000, 2010, 10, 10);
        staff[2] = new Employee("Hong Ma", 26000, 2014, 4, 20);

        for (Employee e : staff) {
            System.out.printf("name=%s,salary=%.2f\n", e.getName(), e.getSalary());
        }
    }
}
