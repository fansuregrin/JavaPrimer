package fansuregrin.corejava.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.Random;

public class ProxyTest {
    public static void main(String[] args) {
        Object[] elements = new Object[1000];
        for (int i=0; i<elements.length; ++i) {
            Integer x = i + 1;
            elements[i] = Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(),
                new Class[] {Comparable.class}, new TraceHandler(x));
        }

        Integer key = new Random().nextInt(elements.length) + 1;
        int idx = Arrays.binarySearch(elements, key);
        if (idx >= 0) System.out.println(elements[idx]);
    }
}

class TraceHandler implements InvocationHandler {
    private Object target;

    public TraceHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
        // print the implicit argument
        System.out.print(target);
        // print the method name
        System.out.print("." + m.getName() + "(");
        // print explicit arguments
        if (args != null) {
            for (int i=0; i<args.length; ++i) {
                if (i > 0) System.out.print(",");
                System.out.print(args[i]);
            }
        }
        System.out.println(")");
        return m.invoke(target, args);
    }
}