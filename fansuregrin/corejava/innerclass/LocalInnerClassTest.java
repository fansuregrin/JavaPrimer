package fansuregrin.corejava.innerclass;

import java.time.Instant;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Toolkit;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class LocalInnerClassTest {
    public static void main(String[] args) {
        TalkingClock2 clock = new TalkingClock2();
        clock.start(2000, true);

        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

class TalkingClock2 {
    public void start(int interval, boolean beep) {
        class TimePrinter implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("At the tone, the time is "
                    + Instant.ofEpochMilli(e.getWhen()));
                if (beep) Toolkit.getDefaultToolkit().beep();
            }
        }

        TimePrinter listener = new TimePrinter();
        Timer timer = new Timer(interval, listener);
        timer.start();
    }
}