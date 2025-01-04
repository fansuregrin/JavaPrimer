# 嵌套类（Nested Classes）
> 嵌套类分为非静态和静态两类，非静态嵌套类称为内部类，声明为静态的嵌套类称为静态嵌套类。
## 非静态嵌套类（Non-static Nested Classes）
非静态的嵌套类通常称为内部类（在 Core Java 一书中内部类就表示嵌套类，这里没有采用这种说法）。
与实例方法和变量一样，内部类与其外围类（enclosing class）的实例相关联，并可直接访问该对象的方法和字段。
此外，由于内部类与实例相关联，因此它本身不能定义任何静态成员。
普通的内部类通常称为*成员内部类*，还有两种特殊的内部类称为*局部内部类*和*匿名内部类*。

### 成员内部类（Member Inner Classes）
Core Java 中提供了一个有关内部类的示例：[InnerClassTest.java](./InnerClassTest.java)。
主要代码如下：
```java
public class InnerClassTest {
    public static void main(String[] args) {
        TalkingClock clock = new TalkingClock(2000, true);
        clock.start();

        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

class TalkingClock {
    private int interval;
    private boolean beep;

    public TalkingClock(int interval, boolean beep) {
        this.interval = interval;
        this.beep = beep;
    }

    public void start() {
        Timer timer = new Timer(interval, new TimePrinter());
        timer.start();
    }

    public class TimePrinter implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.out.println("At the tone, the time is "
                + Instant.ofEpochMilli(e.getWhen()));
            if (beep) Toolkit.getDefaultToolkit().beep();
        }
    }
}
```
其中，`TimePrinter` 是 `TalkingClock` 的内部类，它在 `actionPerformed` 方法中可以直接访问外围类实例的私有字段 `beep` 而没有权限限制。

那么，Java 是如何实现内部类对外围类的字段访问的呢？
在 Java 11 之前，这是由编译器实现的，从 Java 11 开始，由编译器和 JVM 共同实现。
因此，这里基于 Java 8 和 Java 17 进行分析。

#### (1) Java 8 对于内部类的实现原理
在 Java 8 中，内部类由编译器实现。首先，使用编译器将源码编译成字节码：
```
javac .\fansuregrin\corejava\innerclass\InnerClassTest.java
```
会发现，在 `innerclass` 目录下，生成了 3 个 class 文件。
除了 `InnerClassTest.class` 和 `TalkingClock.class`，编译器还生成了 `TalkingClock$TimePrinter.class`。
`TalkingClock$TimePrinter.class` 就代表内部类 `TimePrinter`，编译器对其进行了重命名，名字由外围类名、美元符号和内部类名组合而成。
编译器将内部类和外围类分离，内部类 `TimePrinter` 被编译器一个独立的普通类，JVM 对于 `TimePrinter` 和 `TalkingClock` 的嵌套关系是无感知的。

那么，对于 `TalkingClock$TimePrinter`，它是如何访问 `TalkingClock` 中的私有字段的呢？
编译器通过在 `TalkingClock$TimePrinter` 和 `TalkingClock` 中添加额外的字段和方法来实现这个功能的。
下面`javap`反编译（在 unix 系统上使用命令时需要对类名中的 $ 进行转义）来查看编译后两个类的详细信息：
```
$ javap '.\fansuregrin\corejava\innerclass\TalkingClock$TimePrinter.class'
Compiled from "InnerClassTest.java"
public class fansuregrin.corejava.innerclass.TalkingClock$TimePrinter implements java.awt.event.ActionListener {
  final fansuregrin.corejava.innerclass.TalkingClock this$0;
  public fansuregrin.corejava.innerclass.TalkingClock$TimePrinter(fansuregrin.corejava.innerclass.TalkingClock);
  public void actionPerformed(java.awt.event.ActionEvent);
}

$ javap '.\fansuregrin\corejava\innerclass\TalkingClock.class'
Compiled from "InnerClassTest.java"
class fansuregrin.corejava.innerclass.TalkingClock {
  public fansuregrin.corejava.innerclass.TalkingClock(int, boolean);
  public void start();
  static boolean access$000(fansuregrin.corejava.innerclass.TalkingClock);
}
```
`TalkingClock$TimePrinter` 中多了一个字段 `this$0`，并由构造器 `TalkingClock$TimePrinter(fansuregrin.corejava.innerclass.TalkingClock)` 初始化，`this$0` 引用 `TalkingClock` 的一个实例。
`TalkingClock` 中多了一个静态方法 `static boolean access$000(fansuregrin.corejava.innerclass.TalkingClock)`，它返回一个 `TalkingClock` 对象中的 `beep` 字段的值。
在 `TalkingClock$TimePrinter` 的 `actionPerformed` 方法中，就是通过 `access$000(this$0)` 来获取 `beep` 的。

再看 `TalkingClock$TimePrinter` 字节码，确实如此：
```
{
  final fansuregrin.corejava.innerclass.TalkingClock this$0;
    descriptor: Lfansuregrin/corejava/innerclass/TalkingClock;
    flags: ACC_FINAL, ACC_SYNTHETIC

  public fansuregrin.corejava.innerclass.TalkingClock$TimePrinter(fansuregrin.corejava.innerclass.TalkingClock);
    descriptor: (Lfansuregrin/corejava/innerclass/TalkingClock;)V
    flags: ACC_PUBLIC
    Code:
      stack=2, locals=2, args_size=2
         0: aload_0
         1: aload_1
         2: putfield      #1                  // Field this$0:Lfansuregrin/corejava/innerclass/TalkingClock;
         5: aload_0
         6: invokespecial #2                  // Method java/lang/Object."<init>":()V
         9: return
      LineNumberTable:
        line 35: 0

  public void actionPerformed(java.awt.event.ActionEvent);
    descriptor: (Ljava/awt/event/ActionEvent;)V
    flags: ACC_PUBLIC
    Code:
      stack=4, locals=2, args_size=2
         0: getstatic     #3                  // Field java/lang/System.out:Ljava/io/PrintStream;
         3: new           #4                  // class java/lang/StringBuilder
         6: dup
         7: invokespecial #5                  // Method java/lang/StringBuilder."<init>":()V
        10: ldc           #6                  // String At the tone, the time is
        12: invokevirtual #7                  // Method java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        15: aload_1
        16: invokevirtual #8                  // Method java/awt/event/ActionEvent.getWhen:()J
        19: invokestatic  #9                  // Method java/time/Instant.ofEpochMilli:(J)Ljava/time/Instant;
        22: invokevirtual #10                 // Method java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        25: invokevirtual #11                 // Method java/lang/StringBuilder.toString:()Ljava/lang/String;
        28: invokevirtual #12                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        31: aload_0
        32: getfield      #1                  // Field this$0:Lfansuregrin/corejava/innerclass/TalkingClock;
        35: invokestatic  #13                 // Method fansuregrin/corejava/innerclass/TalkingClock.access$000:(Lfansuregrin/corejava/innerclass/TalkingClock;)Z
        38: ifeq          47
        41: invokestatic  #14                 // Method java/awt/Toolkit.getDefaultToolkit:()Ljava/awt/Toolkit;
        44: invokevirtual #15                 // Method java/awt/Toolkit.beep:()V
        47: return
      LineNumberTable:
        line 38: 0
        line 39: 16
        line 38: 28
        line 40: 31
        line 41: 47
      StackMapTable: number_of_entries = 1
        frame_type = 47 /* same */
}
```
其中，`32: getfield #1` 和 `35: invokestatic #13` 佐证了这一点。

总之，在 Java 8 中，内部类如何访问外围类，是由编译器实现的。
编译器将相应的访问逻辑编译器字节码，JVM 执行就行，JVM 并不知道内部类和外围类之间的联系。
但是，这会带来安全问题，编译器在外围类中添加的静态方法（隐秘方法）具有包可见性。
如果将一个攻击类置于与外围类相同的包中，再手动修改攻击类的字节码，就可以通过外围类中的隐秘方法获取外围类的对象中的私有数据了。
因此，在 Java 11 中，对这个安全问题进行了修补，编译器不再为外围类生成隐秘方法。

#### (2) Java 17 对于内部类的实现原理

Java 11 引入了 *NestHost* 和 *NestMembers* 字节码属性，允许类文件表示它们属于所谓的“嵌套”。
属于同一嵌套的所有类都可以访问彼此的私有成员。
> One nest member (typically the top-level class) is designated as the nest host, and contains an attribute (NestMembers) to identify the other statically known nest members. 
> Each of the other nest members has an attribute (NestHost) to identify its nest host.

通过 `javap` 查看 `TalkingClock` 和 `TalkingClock$TimePrinter` 的字节码:
```
$ javap -v TalkingClock
...
class fansuregrin.corejava.innerclass.TalkingClock
  minor version: 0
  major version: 61
  flags: (0x0020) ACC_SUPER
  this_class: #8                          // fansuregrin/corejava/innerclass/TalkingClock
  super_class: #2                         // java/lang/Object
  interfaces: 0, fields: 2, methods: 2, attributes: 3
...
SourceFile: "InnerClassTest.java"
NestMembers:
  fansuregrin/corejava/innerclass/TalkingClock$TimePrinter
InnerClasses:
  public #37= #19 of #8;                  // TimePrinter=class fansuregrin/corejava/innerclass/TalkingClock$TimePrinter of class fansuregrin/corejava/innerclass/TalkingClock

$
...
public class fansuregrin.corejava.innerclass.TalkingClock$TimePrinter implements java.awt.event.ActionListener
  minor version: 0
  major version: 61
  flags: (0x0021) ACC_PUBLIC, ACC_SUPER
  this_class: #2                          // fansuregrin/corejava/innerclass/TalkingClock$TimePrinter
  super_class: #8                         // java/lang/Object
  interfaces: 1, fields: 1, methods: 2, attributes: 4
...
SourceFile: "InnerClassTest.java"
NestHost: class fansuregrin/corejava/innerclass/TalkingClock
BootstrapMethods:
  0: #67 REF_invokeStatic java/lang/invoke/StringConcatFactory.makeConcatWithConstants:(Ljava/lang/invoke/MethodHandles$Lookup;Ljava/lang/String;Ljava/lang/invoke/MethodType;Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/invoke/CallSite;
    Method arguments:
      #73 At the tone, the time is \u0001
InnerClasses:
  public #76= #2 of #42;                  // TimePrinter=class fansuregrin/corejava/innerclass/TalkingClock$TimePrinter of class fansuregrin/corejava/innerclass/TalkingClock
  public static final #81= #77 of #79;    // Lookup=class java/lang/invoke/MethodHandles$Lookup of class java/lang/invoke/MethodHandles
```
可以看到 `TalkingClock` 中的 `NestMembers` 属性和 `TalkingClock$TimePrinter` 中的 `NestHost` 属性。

JVM 会利用这两个属性，来判断嵌套关系，从而允许内部类对外围类的访问。

再看 `TalkingClock$TimePrinter` 的字节码：
```
{
  final fansuregrin.corejava.innerclass.TalkingClock this$0;
    descriptor: Lfansuregrin/corejava/innerclass/TalkingClock;
    flags: (0x1010) ACC_FINAL, ACC_SYNTHETIC

  public fansuregrin.corejava.innerclass.TalkingClock$TimePrinter(fansuregrin.corejava.innerclass.TalkingClock);
    descriptor: (Lfansuregrin/corejava/innerclass/TalkingClock;)V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=2, locals=2, args_size=2
         0: aload_0
         1: aload_1
         2: putfield      #1                  // Field this$0:Lfansuregrin/corejava/innerclass/TalkingClock;
         5: aload_0
         6: invokespecial #7                  // Method java/lang/Object."<init>":()V
         9: return
      LineNumberTable:
        line 35: 0

  public void actionPerformed(java.awt.event.ActionEvent);
    descriptor: (Ljava/awt/event/ActionEvent;)V
    flags: (0x0001) ACC_PUBLIC
    Code:
      stack=3, locals=2, args_size=2
         0: getstatic     #13                 // Field java/lang/System.out:Ljava/io/PrintStream;
         3: aload_1
         4: invokevirtual #19                 // Method java/awt/event/ActionEvent.getWhen:()J
         7: invokestatic  #25                 // Method java/time/Instant.ofEpochMilli:(J)Ljava/time/Instant;
        10: invokedynamic #31,  0             // InvokeDynamic #0:makeConcatWithConstants:(Ljava/time/Instant;)Ljava/lang/String;
        15: invokevirtual #35                 // Method java/io/PrintStream.println:(Ljava/lang/String;)V
        18: aload_0
        19: getfield      #1                  // Field this$0:Lfansuregrin/corejava/innerclass/TalkingClock;
        22: getfield      #41                 // Field fansuregrin/corejava/innerclass/TalkingClock.beep:Z
        25: ifeq          34
        28: invokestatic  #47                 // Method java/awt/Toolkit.getDefaultToolkit:()Ljava/awt/Toolkit;
        31: invokevirtual #53                 // Method java/awt/Toolkit.beep:()V
        34: return
      LineNumberTable:
        line 38: 0
        line 39: 4
        line 38: 15
        line 40: 18
        line 41: 34
      StackMapTable: number_of_entries = 1
        frame_type = 34 /* same */
}
```
在方法 `actionPerformed` 中，直接通过 `22: getfield #41` 获取 `beep`。

### 局部内部类（Local Inner Classes）
局部类是在块中定义的类，块是一组在括号之间包含零个或多个的语句。
局部类通常会出现在方法中，它对外完全隐藏。
与其他内部类相比，局部类不仅能访问外部类的字段，还可以访问局部变量。
这些局部变量必须是事实最终变量（effectively final）。

[LocalInnerClassTest](./LocalInnerClassTest.java) ：
```java
public class LocalInnerClassTest {
    public static void main(String[] args) {
        TalkingClock2 clock = new TalkingClock2();
        clock.start(2000, true);

        JOptionPane.showMessageDialog(null, "Quit program?");
        System.exit(0);
    }
}

class TalkingClock2 {
    public void start(int interval, boolean beep) {
        class TimePrinter implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("At the tone, the time is "
                    + Instant.ofEpochMilli(e.getWhen()));
                if (beep) Toolkit.getDefaultToolkit().beep();
            }
        }

        TimePrinter listener = new TimePrinter();
        Timer timer = new Timer(interval, listener);
        timer.start();
    }
}
```
`TimePrinter` 是一个位于方法 `TalkingClock2.start(int, boolean)` 中的局部类，
它能够访问局部变量 `beep`。
这里有个疑问，方法 `TimePrinter.actionPerformed(ActionEvent)` 在 `timer.start()` 之后执行，
此时，局部变量 `beep` 已经没有了，`actionPerformed` 中对 `beep` 的访问是否有效？
答案是，对 `beep` 的访问仍然有效。
编译器在编译局部类 `TimePrinter` 时，给其添加了一个字段，并用局部变量 `beep` 初始化这个字段。

编译器编译后，局部类 `TimePrinter` 被编译成 `TalkingClock2$1TimePrinter.class`。
使用 `javap` 反编译查看：
```
Compiled from "LocalInnerClassTest.java"
class fansuregrin.corejava.innerclass.TalkingClock2$1TimePrinter implements java.awt.event.ActionListener {
  final boolean val$beep;
  final fansuregrin.corejava.innerclass.TalkingClock2 this$0;
  fansuregrin.corejava.innerclass.TalkingClock2$1TimePrinter();
  public void actionPerformed(java.awt.event.ActionEvent);
}
```
字段 `val$beep` 保存局部变量 `beep`，`this$0` 引用外部类的对象。

### 匿名内部类（Anonymous Inner Classes）
当不需要显示定义一个类时，可以进一步省略类名，定义一个匿名内部类。
具体语法如下：
```
new SuperClass(construction paramters) {
    inner class methods and data
}

or

new InterfaceType() {
    methods and data
}
```
匿名内部类没有类名，所以不能有构造器，但是可以有对象初始化块。

[AnonymousInnerClass](./AnonymousInnerClassTest.java)：
```java
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
```
`start(int,boolean)` 方法中定义了一个匿名内部类，它实现了接口 `ActionListener`。
编译器会将这个匿名内部类编译成 `TalkingClock3$1.class`：
```
Compiled from "AnonymousInnerClassTest.java"
class fansuregrin.corejava.innerclass.TalkingClock3$1 implements java.awt.event.ActionListener {
  final boolean val$beep;
  final fansuregrin.corejava.innerclass.TalkingClock3 this$0;
  fansuregrin.corejava.innerclass.TalkingClock3$1();
  public void actionPerformed(java.awt.event.ActionEvent);
}
```
其中的字段和方法与局部类类似。

## 静态嵌套类（Static Nested Classes）

# 参考
- Core Java Volume 1 - 6.3 Inner Classes
- [The Java™ Tutorials - Nested Classes](https://docs.oracle.com/javase/tutorial/java/javaOO/nested.html)
- [JEP 181: Nest-Based Access Control](https://openjdk.org/jeps/181)
- [How do inner class access enclosing class' private members in higher versions of Java?](https://stackoverflow.com/questions/72213889/how-do-inner-class-access-enclosing-class-private-members-in-higher-versions-of)