package fansuregrin.corejava.objectanalyzer;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

public class ObjectAnalyzer {
    private ArrayList<Object> visited = new ArrayList<>();

    public String toString(Object obj) throws ReflectiveOperationException {
        if (obj == null) return "null";
        if (visited.contains(obj)) return "...";
        visited.add(obj);
        
        Class<?> clz = obj.getClass();
        
        if (clz == String.class) return (String) obj;
        
        if (clz.isArray()) {
            Class<?> t =  clz.getComponentType();
            String r = t + "[]{";
            for (int i=0; i<Array.getLength(obj); ++i) {
                if (i > 0) r += ",";
                Object val = Array.get(obj, i);
                if (t.isPrimitive()) r += val;
                else r += toString(val);
            }
            return r + '}';
        }

        String r = clz.getName();
        do {
            r += "[";
            Field[] fields = clz.getDeclaredFields();
            AccessibleObject.setAccessible(fields, true);
            for (Field f : fields) {
                if (!Modifier.isStatic(f.getModifiers())) {
                    if (!r.endsWith("[")) r += ",";
                    r += f.getName() + "=";
                    Object val = f.get(obj);
                    if (f.getType().isPrimitive()) r += val;
                    else r += toString(val);
                }
            }
            r += "]";
            clz = clz.getSuperclass();
        } while (clz != null);
        return r;
    }
}
