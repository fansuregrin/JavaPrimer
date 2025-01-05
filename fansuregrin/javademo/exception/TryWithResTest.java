package fansuregrin.javademo.exception;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class TryWithResTest {
    public static void main(String[] args) throws IOException {
        String filepath = null;
        if (args.length > 0) {
            filepath = args[0];
        } else {
            System.out.print("Enter file path: ");
            Scanner stdin = new Scanner(System.in);
            filepath = stdin.nextLine();
            stdin.close();
        }

        // `Scanner` implements the interface `Closeable`.
        // When leaving the try block, `in.close()` is automatically called.
        
        // try with resources
        try (Scanner in = new Scanner(new FileInputStream(filepath), StandardCharsets.UTF_8)) {
            while (in.hasNextLine()) {
                System.out.println(in.nextLine());
            }
        } // in.close() called here
    }  
}