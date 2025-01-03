package fansuregrin.corejava.resource;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ResourceTest {
    public static void main(String[] args) throws IOException {
        Class<?> clazz = ResourceTest.class;
        URL aboutUrl = clazz.getResource("about.png");
        var icon = new ImageIcon(aboutUrl);

        InputStream stream = clazz.getResourceAsStream("data/about.txt");
        var about = new String(stream.readAllBytes(), StandardCharsets.UTF_8);

        InputStream stream2 = clazz.getResourceAsStream("data/title.txt");
        var title = new String(stream2.readAllBytes(), StandardCharsets.UTF_8).trim();

        JOptionPane.showMessageDialog(null, about, title,
            JOptionPane.INFORMATION_MESSAGE, icon);
    }
}
