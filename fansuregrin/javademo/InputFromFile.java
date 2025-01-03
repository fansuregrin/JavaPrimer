package fansuregrin.javademo;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Scanner;
import java.io.File;

public class InputFromFile {
    public static void main(String[] args) throws Exception {
        String path = InputFromFile.class.getResource("InputFromFile.class").getPath();
        String classDir = new File(path).getParent();
        Scanner in = new Scanner(Paths.get(classDir, "in.txt"),
            StandardCharsets.UTF_8);
        PrintWriter out = new PrintWriter("out.txt", StandardCharsets.UTF_8);
        while (in.hasNextLine()) {
            String line = in.nextLine();
            out.print(line);
            if (in.hasNextLine()) {
                out.println();
            }
        }
        in.close();
        out.close();
    }
}
