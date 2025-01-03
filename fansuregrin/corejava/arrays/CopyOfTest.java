package fansuregrin.corejava.arrays;

import java.lang.reflect.Array;
import java.util.Arrays;

public class CopyOfTest {
    public static void main(String[] args) {
        int[] a = {1, 2, 3};
        a = (int[]) goodCopyOf(a, 10);
        System.out.println(Arrays.toString(a));

        String[] b = {"Cat", "Dog", "Pig"};
        b = (String[]) goodCopyOf(b, 5);
        System.out.println(Arrays.toString(b));

        // generate `java.lang.ClassCastException`
        b = (String[]) badCopyOf(b, 6);
    }

    public static Object[] badCopyOf(Object[] a, int newLength) {
        var newArray = new Object[newLength];
        System.arraycopy(a, 0, newArray, 0, Math.min(a.length, newLength));
        return newArray;
    }

    public static Object goodCopyOf(Object a, int newLength) {
        Class<?> clz = a.getClass();
        if (!clz.isArray()) return null;

        Class<?> t = clz.getComponentType();
        Object newArray = Array.newInstance(t, newLength);
        int length = Array.getLength(a);
        System.arraycopy(a, 0, newArray, 0, length);
        return newArray;
    }
}
