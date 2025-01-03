package fansuregrin.corejava.lambda;

import java.util.Arrays;
import java.awt.Toolkit;
import java.time.Instant;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class LambdaTest {
    public static void main(String[] args) {
        var planets = new String[] {"Mercury", "Venus", "Earth", "Mars", "Jupiter",
            "Saturn", "Uranus", "Neptune"};
        System.out.println("before sort: " + Arrays.toString(planets));
        Arrays.sort(planets);
        System.out.println("sorted in dictionary order: " + Arrays.toString(planets));
        // use a lambda as the comparator
        Arrays.sort(planets, (a, b) -> a.length() - b.length());
        System.out.println("sorted by length: " + Arrays.toString(planets));
        
        // use a lambda as the action listener
        var timer = new Timer(1000, e -> {
            System.out.println(Instant.ofEpochMilli(e.getWhen()));
            Toolkit.getDefaultToolkit().beep();
        });
        timer.start();
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}
