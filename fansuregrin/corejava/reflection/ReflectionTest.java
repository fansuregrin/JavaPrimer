package fansuregrin.corejava.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.util.Scanner;

public class ReflectionTest {
    public static void main(String[] args) throws ReflectiveOperationException {
        String name = null;
        if (args.length < 1) {
            System.out.print("Enter class name: ");
            var in = new Scanner(System.in);
            name = in.next().trim();
            in.close();
        } else {
            name = args[0];
        }

        Class<?> clz = Class.forName(name);
        Class<?> superClass = clz.getSuperclass();
        String modifiers = Modifier.toString(clz.getModifiers());
        if (modifiers.length() > 0) System.out.print(modifiers + " ");
        System.out.print(name);
        if (superClass != null && superClass != Object.class) {
            System.out.print(" extends " + superClass.getName());
        }

        System.out.println(" {");
        printFields(clz);
        System.out.println();
        printConstructors(clz);
        System.out.println();
        printMethods(clz);
        System.out.println("}");
    }

    public static void printConstructors(Class<?> clz) {
        Constructor<?>[] constructors = clz.getDeclaredConstructors();
        for (Constructor<?> ctor : constructors) {
            String name = ctor.getName();
            System.out.print("  ");
            
            String modifiers = Modifier.toString(ctor.getModifiers());
            if (modifiers.length() > 0) {
                System.out.print(modifiers + " ");
            }
            System.out.print(name + "(");
            
            Class<?>[] paramTypes = ctor.getParameterTypes();
            for (int i=0; i<paramTypes.length; ++i) {
                if (i > 0) System.out.print(", ");
                System.out.print(paramTypes[i].getName());
            }
            System.out.println(");");
        }
    }

    public static void printMethods(Class<?> clz) {
        Method[] methods = clz.getDeclaredMethods();
        for (Method mtd : methods) {
            System.out.print("  ");
            
            String modifiers = Modifier.toString(mtd.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");
            
            System.out.print(mtd.getName());

            System.out.print("(");
            Class<?>[] paramTypes = mtd.getParameterTypes();
            for (int i=0; i<paramTypes.length; ++i) {
                if (i > 0) System.out.print(", ");
                System.out.print(paramTypes[i].getName());
            }
            System.out.println(");");
        }
    }

    public static void printFields(Class<?> clz) {
        Field[] fields = clz.getDeclaredFields();
        for (Field fld : fields) {
            System.out.print("  ");
            
            String modifiers = Modifier.toString(fld.getModifiers());
            if (modifiers.length() > 0) System.out.print(modifiers + " ");

            System.out.println(fld.getType().getName() + " " + fld.getName());
        }
    }
}
