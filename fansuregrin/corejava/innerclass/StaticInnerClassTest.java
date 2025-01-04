package fansuregrin.corejava.innerclass;

import java.util.Arrays;

public class StaticInnerClassTest {
    public static void main(String[] args) {
        double[] arr = new double[10];
        for (int i=0; i<arr.length; ++i) {
            arr[i] = Math.random() * 100;
        }
        System.out.println("arr: " + Arrays.toString(arr));
        ArrayAlg.Pair minMax = ArrayAlg.minmax(arr);
        System.out.println("min value of arr: " + minMax.getFirst());
        System.out.println("max value of arr: " + minMax.getSecond());
    }
}

class ArrayAlg {
    public static class Pair {
        private double first;
        private double second;

        public Pair(double first, double second) {
            this.first = first;
            this.second = second;
        }

        public double getFirst() { return first; }
        public double getSecond() { return second; }
    }

    public static Pair minmax(double[] arr) {
        double min = Double.POSITIVE_INFINITY;
        double max = Double.NEGATIVE_INFINITY;
        for (double x : arr) {
            if (x < min) min = x;
            if (x > max) max = x;
        }
        return new Pair(min, max);
    }
}