package fansuregrin.corejava.methods;
import java.lang.reflect.Method;

/**
 * The {@code MethodTableTest} class demonstrates the use of reflection to invoke methods
 * and print a table of function values.
 * 
 * <p>This class contains a {@code main} method that retrieves the {@code square} method
 * from the {@code MethodTableTest} class and the {@code sqrt} method from the {@code Math}
 * class using reflection. It then prints tables of values for these functions over a specified
 * range.
 * 
 * <p>The {@code square} method calculates the square of a given number.
 * 
 * <p>The {@code printTable} method prints a table of function values for a given method
 * over a specified range.
 * 
 * @throws ReflectiveOperationException if there is an error in method retrieval or invocation
 */
public class MethodTableTest {
    public static void main(String[] args) throws ReflectiveOperationException {
        Method square = MethodTableTest.class.getMethod("square", double.class);
        Method sqrt = Math.class.getMethod("sqrt", double.class);
        printTable(1, 10, 10, square);
        printTable(1, 10, 10, sqrt);
    }

    
    public static double square(double x) {
        return x * x;
    }

    public static void printTable(double from, double to, int n, Method f)
    throws ReflectiveOperationException {
        System.out.println(f);

        double dx = (to - from) / (n - 1);
        for (double x = from; x <= to; x += dx) {
            double y = (Double) f.invoke(null, x);
            System.out.printf("%10.4f | %10.4f%n", x, y);
        }
    }
}
