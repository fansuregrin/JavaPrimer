package fansuregrin.javademo.exception;

import java.io.FileInputStream;
import java.io.IOException;

public class FinallyTest {
    public static void main(String[] args) throws IOException {
        FileInputStream in = null;
        try {
            in = new FileInputStream("not_exist.txt");
        } catch (IOException e) {
            System.out.println(e);
            throw new IOException(e);
        } finally {
            System.out.println("try to close the stream...");
            if (in != null) in.close();
        }
    }
}