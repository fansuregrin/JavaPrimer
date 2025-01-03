package fansuregrin.corejava.objectanalyzer;

import java.util.ArrayList;

// '--add-opens' 'java.base/java.util=ALL-UNNAMED'
// '--add-opens' 'java.base/java.lang=ALL-UNNAMED'
public class ObjectAnalyzerTest {
    public static void main(String[] args) throws ReflectiveOperationException {
        var squares = new ArrayList<Integer>();
        for (int i=0; i<5; ++i) squares.add(i * i);
        System.out.println(new ObjectAnalyzer().toString(squares));
    }
}
