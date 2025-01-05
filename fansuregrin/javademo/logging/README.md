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

# 参考
- [Logger Manager - JDK 17 API Doc](https://docs.oracle.com/en/java/javase/17/docs/api/java.logging/java/util/logging/LogManager.html)
- [logging.properties example - Mkyong.com](https://mkyong.com/logging/logging-properties-example/)