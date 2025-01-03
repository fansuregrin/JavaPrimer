package fansuregrin.javademo;

import java.util.Scanner;

public class InputTest {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        System.out.print("What's your name:");
        String name = in.nextLine();
        System.out.print("How old are you:");
        int age = in.nextInt();
        System.out.printf("Hello, %s. Next year, you'll be %d!", name, age+1);
        in.close();
    }
}
