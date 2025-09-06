package factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class EmulatorManager {

  private static Process emulatorProcess;
  private static String runType = System.getProperty("runType").toLowerCase();

  public static void startEmulator() throws IOException, InterruptedException {
    if (runType == null || runType.equals("remote")) {
      return;
    }
    System.out.println("🔄 Starting Android Emulator...");
    String emulatorPath = "/Users/gevorg.kocharyan/Library/Android/sdk/emulator/emulator";
    ProcessBuilder builder = new ProcessBuilder(emulatorPath, "-avd", "Pixel", "-netdelay", "none",
        "-netspeed", "full", "-no-snapshot");
    builder.inheritIO();
    emulatorProcess = builder.start();
    waitForDevice();
    System.out.println("✅ Emulator started and ready.");
  }

  public static void stopEmulator() throws IOException, InterruptedException {
    if (runType == null || runType.equals("remote")) {
      return;
    }
    System.out.println("🛑 Stopping Android Emulator...");
    Process killProcess = new ProcessBuilder("adb", "emu", "kill").start();
    killProcess.waitFor();
    System.out.println("✅ Emulator stopped.");
  }

  private static void waitForDevice() throws IOException, InterruptedException {
    System.out.println("⏳ Waiting for emulator to fully boot...");
    while (true) {
      Process checkBoot = new ProcessBuilder("adb", "shell", "getprop",
          "sys.boot_completed").start();
      try (BufferedReader reader = new BufferedReader(
          new InputStreamReader(checkBoot.getInputStream(), StandardCharsets.UTF_8))) {
        String line = reader.readLine();
        if (line != null && line.trim().equals("1")) {
          System.out.println("📱 Emulator boot completed.");
          break;
        }
      }
      Thread.sleep(15000);
    }
    new ProcessBuilder("adb", "shell", "input", "keyevent", "82").start();
  }
}
