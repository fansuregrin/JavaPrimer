package fansuregrin.javademo.logging;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class LogTest {
    private static final Logger myLogger = Logger.getLogger(
        "fansuregrin.javademo.logging.LogTest");
    private static final Logger localeLogger = Logger.getLogger(  
        "fansuregrin.javademo.logging.LogTest.i18n",
        "fansuregrin.javademo.logging.logmessages");
    
    public static void main(String[] args) throws IOException {
        // global logger
        Logger.getGlobal().info("test");
        Logger.getGlobal().info("hi");

        // 7 levels:
        // - SEVERE
        // - WARNING
        // - INFO
        // - CONFIG
        // - FINE
        // - FINER
        // - FINEST
        myLogger.setLevel(Level.FINE);
        // why? see `Logger.log(LogRecord record)`.
        // myLogger has no handler, but its parent logger has a ConsoleHandler,
        // so this log record will be pubished to that ConsoleHandler.
        // However, the ConsoleHandler's log level is 'INFO', 
        // so this log message won't be output to the console.
        myLogger.fine("ok"); // no effective
        myLogger.log(Level.INFO, "123");

        // trace execution flow and log exceptions
        byte[] buf = new byte[1024];
        read("a.txt", buf);
        
        // modify LoggerManager config
        System.setProperty("java.util.logging.config.file",
            new File(LogTest.class.getResource("logging.properties").getPath())
            .toPath().toString());
        LogManager.getLogManager().readConfiguration();
        myLogger.fine("fine");

        // localization
        localeLogger.log(Level.INFO, "readFile", "a.txt");
        localeLogger.log(Level.INFO, "renameFile", new Object[] {"a.txt", "b.txt"});
        Locale.setDefault(Locale.CHINA);
        localeLogger.log(Level.INFO, "readFile", "a.txt");
        localeLogger.log(Level.INFO, "renameFile", new Object[] {"a.txt", "b.txt"});

        // add handler
        myLogger.setUseParentHandlers(false);
        myLogger.addHandler(new FileHandler());
        myLogger.fine("hello");
        // log messages will be flushed into a log file
        // default filename pattern of the log file: %h/java%u.log
        // A pattern consists of a string that includes the following special
        // components that will be replaced at runtime:
        // "/" the local pathname separator
        // "%t" the system temporary directory
        // "%h" the value of the "user.home" system property
        // "%g" the generation number to distinguish rotated logs
        // "%u" a unique number to resolve conflicts
        // "%%" translates to a single percent sign "%"
    }

    public static int read(String file, byte[] buf) throws IOException {
        String className = "fansuregrin.javademo.logging.LogTest";
        String methodName = "read";
        myLogger.entering(className, methodName, new Object[] {file, buf});
        int count = 0;
        try (FileInputStream stream = new FileInputStream(file)) {
            count = stream.read(buf);
        } catch (IOException e) {
            myLogger.log(Level.WARNING, "read a file", e);
        }
        
        myLogger.exiting(className, methodName, count);
        
        return count;
    }
}
