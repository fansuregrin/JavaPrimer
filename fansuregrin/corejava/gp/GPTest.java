package fansuregrin.corejava.gp;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;

public class GPTest {
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
        Pair<ChronoLocalDate> mm2 = ArrayAlg.minmax2(birthdays);
        System.out.println("min = " + mm2.getFirst());
        System.out.println("max = " + mm2.getSecond());

        // Pair<int> a = new Pair<>();
        Pair<Integer> a = new Pair<>();
        
        // if (mm instanceof Pair<T>) {}
        if (mm instanceof Pair<String>) {}
        if (a.getClass() == mm.getClass()) {
            System.out.print("always equal");
        };
        
        // Pair<String>[] a = new Pair<>[10];
    }
}