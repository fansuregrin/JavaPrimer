package fansuregrin.corejava.gp;

public class ArrayAlg {
    public static Pair<String> minmax(String[] a) {
        if (a == null || a.length == 0) return null;
        String min = a[0];
        String max = a[0];
        for (int i=1; i<a.length; ++i) {
            if (a[i].compareTo(min) < 0) min = a[i];
            if (a[i].compareTo(max) > 0) max = a[i];
        }
        return new Pair<>(min, max);
    }

    public static <T extends Comparable<? super T>> Pair<T> minmax2(T[] a) {
        if (a == null || a.length == 0) return null;
        T min = a[0];
        T max = a[0];
        for (int i=1; i<a.length; ++i) {
            if (a[i].compareTo(min) < 0) min = a[i];
            if (a[i].compareTo(max) > 0) max = a[i];
        }
        return new Pair<>(min, max);
    }

    public static <T> T getMiddle(T[] a) {
        if (a == null || a.length == 0) return null;
        return a[a.length/2];
    }
}
