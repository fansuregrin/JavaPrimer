package fansuregrin.javademo;

import java.util.Arrays;
import java.util.Comparator;

public class ComparaorTest {
    public static void main(String[] args) {
        String[] names = {"alpha", "beta", "x", "yes", "no"};
        
        System.out.println("before sort: " + Arrays.toString(names));
        Arrays.sort(names, new LengthComparator());
        System.out.println("after sort: " + Arrays.toString(names));
    }
}

class LengthComparator implements Comparator<String> {
    @Override
    public int compare(String a, String b) {
        return Integer.compare(a.length(), b.length());
    }
}