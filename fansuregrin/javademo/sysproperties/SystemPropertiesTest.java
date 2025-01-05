package fansuregrin.javademo.sysproperties;

import java.util.Properties;

public class SystemPropertiesTest {
    public static void main(String[] args) {
        Properties props = System.getProperties();
        props.forEach((k, v) -> {
            System.out.printf("%-30s %s\n", k, v);
        });
    }
}
