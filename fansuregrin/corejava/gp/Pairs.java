package fansuregrin.corejava.gp;

public class Pairs {
    private Pairs() {}

    public static boolean hasNulls(Pair<?> p) {
        return p.getFirst() == null || p.getSecond() == null;
    }

    public static <T> void swapHelper(Pair<T> p) {
        T first = p.getFirst();
        p.setFirst(p.getSecond());
        p.setSecond(first);
    }

    public static void swap(Pair<?> p) {
        swapHelper(p);
    }
}
