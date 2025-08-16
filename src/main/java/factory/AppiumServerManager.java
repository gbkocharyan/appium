package factory;

import java.io.*;
import java.nio.charset.StandardCharsets;

public abstract class AppiumServerManager {

  private static final String EXECUTION_MODE = System.getProperty("runType").toLowerCase();
  private static final String APPIUM_LOG_FILE = "appium.log";
  private static final String APPIUM_PID_FILE = "appium.pid";

  private static final String SHELL = "bash";
  private static final String COMMAND_FLAG = "-c";
  private static final String KILL_COMMAND = "kill";
  private static final String APP_START_COMMAND =
      "appium --log-level info > " + APPIUM_LOG_FILE + " 2>&1 & echo $! > " + APPIUM_PID_FILE;

  public static void startAppiumServer() throws Exception {
    if (isRemoteExecution()) {
      return;
    }
    ProcessBuilder pb = createProcessBuilder(APP_START_COMMAND);
    pb.directory(getWorkingDirectory());
    pb.start();
    Thread.sleep(5000);
    System.out.println("Appium server started.");
  }

  public static void stopAppiumServer() throws Exception {
    if (isRemoteExecution()) {
      return;
    }

    File pidFile = new File(APPIUM_PID_FILE);
    if (pidFile.exists()) {
      String pid = readPidFromFile(pidFile);
      if (pid != null && !pid.isEmpty()) {
        executeKillCommand(pid);
        if (!pidFile.delete()) {
          System.err.println("Warning: Failed to delete PID file " + pidFile.getAbsolutePath());
        }
        System.out.println("Appium server stopped.");
      }
    } else {
      System.out.println("Appium PID file not found. Is Appium running?");
    }
  }

  private static boolean isRemoteExecution() {
    return EXECUTION_MODE.equals("remote");
  }

  private static ProcessBuilder createProcessBuilder(String command) {
    return new ProcessBuilder(SHELL, COMMAND_FLAG, command);
  }

  private static File getWorkingDirectory() {
    return new File(System.getProperty("user.dir"));
  }

  private static String readPidFromFile(File pidFile) throws IOException {
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(new FileInputStream(pidFile), StandardCharsets.UTF_8))) {
      return reader.readLine();
    }
  }

  private static void executeKillCommand(String pid) throws IOException, InterruptedException {
    ProcessBuilder pb = createProcessBuilder(KILL_COMMAND + " " + pid);
    pb.start().waitFor();
  }
}
