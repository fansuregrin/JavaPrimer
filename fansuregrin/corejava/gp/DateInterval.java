package fansuregrin.corejava.gp;

import java.time.LocalDate;

/**
 * Compiled from "DateInterval.java"
 * <pre>
 * {@code
 * public class fansuregrin.corejava.gp.DateInterval extends 
 * fansuregrin.corejava.gp.Pair<java.time.LocalDate> {
 *     public fansuregrin.corejava.gp.DateInterval();
 *     public void setSecond(java.time.LocalDate);
 *     public java.time.LocalDate getSecond();
 *     public void setSecond(java.lang.Object); // bridge method
 *     public java.lang.Object getSecond(); // bridge method
 * }
 * }
 */
public class DateInterval extends Pair<LocalDate> {
    @Override
    public void setSecond(LocalDate second) {
        if (second.compareTo(getFirst()) >= 0) {
            super.setSecond(second);
        }
    }

    @Override
    public LocalDate getSecond() {
        return (LocalDate) super.getSecond();
    }
}
