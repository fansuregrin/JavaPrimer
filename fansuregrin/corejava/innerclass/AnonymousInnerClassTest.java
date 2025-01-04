package fansuregrin.corejava.innerclass;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import java.time.Instant;

import javax.swing.JOptionPane;
import javax.swing.Timer;

public class AnonymousInnerClassTest {
    public static void main(String[] args) {
        new TalkingClock3().start(2000, true);
        
        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

class TalkingClock3 {
    public void start(int interval, boolean beep) {
        new Timer(interval, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("At the tone, the time is "
                    + Instant.ofEpochMilli(e.getWhen()));
                if (beep) Toolkit.getDefaultToolkit().beep();
            }
        }).start();
    }
}