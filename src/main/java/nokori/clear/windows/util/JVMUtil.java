package nokori.clear.windows.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;
import java.util.List;

public class JVMUtil {
    /**
     * Restarts the Java virtual machine and forces it to run on the first thread IF and only IF it is not currently on the first thread. This allows your LWJGL3 program to run on Mac properly.
     * <br>
     * Your java program must return after calling this method if it returns true as to prevent your application from running twice.
     * <br><br>
     * To implement this method, simply put it on the first line of your main(args) function.
     * <br><br>
     * Credit goes to Spasi on JGO for making this utility.
     * <br><br>
     * Example of usage (first line in main() method):<br>
     * if (LWJGUIUtil.restartJVMOnFirstThread(true, args)) {<br>
     * return;<br>
     * }<br>
     *
     * @param needsOutput - Whether or not the JVM should print to System.out.println
     * @param args        - the usual String[] args used in the main method
     * @return true if the JVM was successfully restarted.
     */
    public static boolean restartJVMOnFirstThread(boolean needsOutput, String... args) {
        // Figure out the right class to call
        StackTraceElement[] cause = Thread.currentThread().getStackTrace();

        boolean foundThisMethod = false;
        String callingClassName = null;
        for (StackTraceElement se : cause) {
            // Skip entries until we get to the entry for this class
            String className = se.getClassName();
            String methodName = se.getMethodName();
            if (foundThisMethod) {
                callingClassName = className;
                break;
            } else if (JVMUtil.class.getName().equals(className) && "restartJVMOnFirstThread".equals(methodName)) {
                foundThisMethod = true;
            }
        }

        if (callingClassName == null) {
            throw new RuntimeException("Error: unable to determine main class");
        }

        try {
            Class<?> theClass = Class.forName(callingClassName, true, Thread.currentThread().getContextClassLoader());

            return restartJVMOnFirstThread(needsOutput, theClass, args);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    /**
     * Restarts the Java virtual machine and forces it to run on the first thread IF and only IF it is not currently on the first thread. This allows your LWJGL3 program to run on Mac properly.
     * <br><br>
     * To implement this method, simply put it on the first line of your main(args) function.
     * <br><br>
     * Credit goes to Spasi on JGO for making this utility.
     * <br><br>
     * Example of usage (first line in main() method):<br>
     * if (LWJGUIUtil.restartJVMOnFirstThread(true, class, args)) {<br>
     * return;<br>
     * }<br>
     *
     * @param needsOutput - Whether or not the JVM should print to System.out.println
     * @param customClass - Class where the main method is stored
     * @param args        - the usual String[] args used in the main method
     * @return true if the JVM was successfully restarted.
     */
    public static boolean restartJVMOnFirstThread(boolean needsOutput, Class<?> customClass, String... args) {

        // If we're already on the first thread, return
        String startOnFirstThread = System.getProperty("XstartOnFirstThread");
        if (startOnFirstThread != null && startOnFirstThread.equals("true"))
            return false;

        // if not a mac then we're already on first thread, return.
        String osName = System.getProperty("os.name");
        if (!osName.startsWith("Mac") && !osName.startsWith("Darwin")) {
            return false;
        }

        // get current jvm process pid
        String pid = ManagementFactory.getRuntimeMXBean().getName().split("@")[0];
        // get environment variable on whether XstartOnFirstThread is enabled
        String env = System.getenv("JAVA_STARTED_ON_FIRST_THREAD_" + pid);

        // if environment variable is "1" then XstartOnFirstThread is enabled
        if (env != null && env.equals("1")) {
            return false;
        }

        // restart jvm with -XstartOnFirstThread
        String separator = System.getProperty("file.separator");
        String classpath = System.getProperty("java.class.path");
        String mainClass = System.getenv("JAVA_MAIN_CLASS_" + pid);
        String jvmPath = System.getProperty("java.home") + separator + "bin" + separator + "java";

        List<String> inputArguments = ManagementFactory.getRuntimeMXBean().getInputArguments();

        ArrayList<String> jvmArgs = new ArrayList<String>();


        jvmArgs.add(jvmPath);
        jvmArgs.add("-XstartOnFirstThread");
        jvmArgs.addAll(inputArguments);
        jvmArgs.add("-cp");
        jvmArgs.add(classpath);

        if (customClass == null) {
            jvmArgs.add(mainClass);
        } else {
            jvmArgs.add(customClass.getName());
        }
        for (int i = 0; i < args.length; i++) {
            jvmArgs.add(args[i]);
        }

        // if you don't need console output, just enable these two lines
        // and delete bits after it. This JVM will then terminate.
        if (!needsOutput) {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(jvmArgs);
                processBuilder.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                ProcessBuilder processBuilder = new ProcessBuilder(jvmArgs);
                processBuilder.redirectErrorStream(true);
                Process process = processBuilder.start();

                InputStream is = process.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String line;

                while ((line = br.readLine()) != null) {
                    System.out.println(line);
                }
                System.exit(0);
                process.waitFor();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
