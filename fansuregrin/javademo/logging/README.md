# 日志

## 日志管理器
`java.util.logging.LogManager`。

有一个全局的 `LogManager` 对象，用于维护一组有关 `Logger` 和日志服务的共享状态。
这个全局 `LogManager` 对象：
- 管理 `Logger` 对象的分层命名空间。所有具名的 `Logger` 都存储在此命名空间中。
- 管理一组日志控制属性。这些控制属性是简单的键值对，可供 `Handler` 和其他日志对象用来配置自身。

可以使用 `LogManager.getLogManager()` 获取全局 `LogManager` 对象。
`LogManager` 对象是在类初始化期间创建的，随后无法更改。
在启动时，可以使用 `java.util.logging.manager` 系统属性来定位 `LogManager` 类。

### 日志管理器配置
`LogManager` 在初始化期间通过 `readConfiguration()` 方法初始化日志配置。
默认情况下，使用 `LogManager` 默认配置。
`LogManager` 读取的日志配置必须采用属性文件格式。
`LogManager` 定义了两个可选的系统属性，允许控制初始配置，如 `readConfiguration()` 方法中指定：
- `java.util.logging.config.class`
- `java.util.logging.config.file`
这两个系统属性可以在命令行中指定给 `java` 命令，也可以作为系统属性定义传递给 `JNI_CreateJavaVM`。

在 Java 8 及其之前的版本中，Java 日志 APIs 默认会从 `$JAVA_HOME/jre/lib/` 加载 `logging.properties` 属性文件。
自 Java 9 开始，`logging.properties` 属性文件移动到了 `$JAVA_HOME/conf` 目录下。
例如，在我的 Windows 11 系统上，能找到：
- `C:\Program Files\Java\jdk1.8.0_202\jre\lib\logging.properties`
- `C:\Program Files\Java\jdk-17\conf\logging.properties`

## 日志类
`Logger` 对象用于记录特定系统或应用程序组件的消息。
`Logger` 通常使用分层的点分隔命名空间来命名。
`Logger` 名称可以是任意字符串，但它们通常应基于记录组件的包名称或类名称，例如 `java.net` 或 `javax.swing`。
此外，还可以创建未存储在 `Logger` 命名空间中的 “匿名” `Logger`。

`Logger` 对象可以通过调用 `getLogger` 工厂方法之一来获取。
这些方法将创建一个新的 `Logger` 或返回一个合适的现有 `Logger`。
需要注意的是，如果没有保留对 `Logger` 的强引用，`getLogger` 工厂方法之一返回的 `Logger` 可能会随时被垃圾回收。
获取 `Logger` 对象的工厂方法：
```java
static Logger getLogger(String name);
static Logger getLogger(String name, String resourceBundleName);
static final Logger getGlobal();
static Logger getAnonymousLogger();
static Logger getAnonymousLogger(String resourceBundleName);
```

日志消息将被转发到已注册的 `Handler` 对象，这些对象可以将消息转发到各种目的地，包括控制台、文件、操作系统日志等。

每个 `Logger` 都会跟踪 “父” `Logger`，即 `Logger` 命名空间中其最近的现有祖先。

每个 `Logger` 都有一个与之关联的“级别”。
这反映了此 logger 关注的最低级别。
如果将 logger 的级别设置为 `null`，则其有效级别将从其父级继承，而父级又可以从其父级递归获取该级别，依此类推。

可以根据日志配置文件中的属性配置日志级别，如 `LogManager` 类的描述中所述。
但是，也可以通过调用 `Logger.setLevel` 方法动态更改它。
如果更改了日志记录器的级别，则更改也可能会影响子日志记录器，因为任何级别为 `null` 的子日志记录器都将从其父级继承其有效级别。

### 日志记录过程
每次日志记录调用时，`Logger` 都会首先根据日志记录器的有效日志级别对请求级别（例如，SEVERE 或 FINE）进行简单检查。
如果请求级别低于日志级别，日志记录调用将立即返回。
通过此初始（简单）测试后，`Logger` 将分配一个 `LogRecord` 来描述日志记录消息。
然后，它将调用 `Filter`（如果存在）来更详细地检查是否应发布记录。
如果通过，它将把 `LogRecord` 发布到其输出处理程序（Handlers）。
默认情况下，日志记录器还会向其父级处理程序（Handlers）发布，并沿树递归向上。
具体代码过程如下：
```java
public void log(Level level, String msg) {
    if (!isLoggable(level)) { // 检查日志级别
        return;
    }
    LogRecord lr = new LogRecord(level, msg); // 分配一个 `LogRecord`
    doLog(lr);
}

private void doLog(LogRecord lr) {
    lr.setLoggerName(name);
    final LoggerBundle lb = getEffectiveLoggerBundle();
    final ResourceBundle  bundle = lb.userBundle;
    final String ebname = lb.resourceBundleName;
    if (ebname != null && bundle != null) {
        lr.setResourceBundleName(ebname);
        lr.setResourceBundle(bundle);
    }
    log(lr);
}

public void log(LogRecord record) {
    if (!isLoggable(record.getLevel())) {
        return;
    }
    Filter theFilter = config.filter;
    if (theFilter != null && !theFilter.isLoggable(record)) {
        return;
    }

    // Post the LogRecord to all our Handlers, and then to
    // our parents' handlers, all the way up the tree.

    Logger logger = this;
    while (logger != null) {
        final Handler[] loggerHandlers = isSystemLogger
            ? logger.accessCheckedHandlers()
            : logger.getHandlers();

        for (Handler handler : loggerHandlers) {
            handler.publish(record);
        }

        final boolean useParentHdls = isSystemLogger
            ? logger.config.useParentHandlers
            : logger.getUseParentHandlers();

        if (!useParentHdls) {
            break;
        }

        logger = isSystemLogger ? logger.parent : logger.getParent();
    }
}
```

### 日志记录方法
日志记录方法分为五个主要类别：
- 有一组 `log` 方法，它们采用日志级别、消息字符串以及可选的消息字符串参数。
- 有一组 `logp` 方法（用于 “log precise”），它们类似于 `log` 方法，但也采用显式源类名和方法名。
- 有一组 `logrb` 方法（用于 “log with resource bundle”），它们类似于 `logp` 方法，但也采用显式资源包对象用于本地化日志消息。
- 有用于跟踪方法入口（`entering` 方法）、方法返回（`exiting`方法）和抛出异常（`throwing`方法）的便捷方法。
- 最后，有一组用于最简单的情况的便捷方法，即开发人员只想在给定的日志级别记录一个简单的字符串。
这些方法以标准级别名称命名（“严重”、“警告”、“信息”等），并采用单个参数，即消息字符串。

对于不采用明确源名称和方法名称的方法，Logging 框架将尽“最大努力”确定调用到日志记录方法中的类和方法。
但是，重要的是要意识到这种自动推断的信息可能只是近似的（甚至可能完全错误！）。
虚拟机在 JIT 时可以进行大量优化，并且可以完全删除堆栈框架，从而无法可靠地找到调用类和方法。

Logger 上的所有方法都是多线程安全的。

# 参考
- [LogManager - JDK 17 API Doc](https://docs.oracle.com/en/java/javase/17/docs/api/java.logging/java/util/logging/LogManager.html)
- [logging.properties example - Mkyong.com](https://mkyong.com/logging/logging-properties-example/)
- [Logger - JDK 17 API Doc](https://docs.oracle.com/en/java/javase/17/docs/api/java.logging/java/util/logging/Logger.html)