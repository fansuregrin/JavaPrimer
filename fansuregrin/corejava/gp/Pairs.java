package fansuregrin.corejava.gp;

public class Pairs {
    private Pairs() {}

    public static boolean hasNulls(Pair<?> p) {
        return p.getFirst() == null || p.getSecond() == null;
    }
}
