package fansuregrin.corejava.stacktrace;

import java.util.Scanner;

public class StackTraceTest {
    public static int factorial(int n) {
        System.out.println("factorial(" + n + "):");
        StackWalker walker = StackWalker.getInstance();
        walker.forEach(System.out::println);

        int r;
        if (n <= 1) r = 1;
        else r = n * factorial(n-1);
        
        System.out.println("return " + r);
        return r;
    }

    public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)) {
            System.out.print("Enter n: ");
            factorial(in.nextInt());
        }
    }
}
