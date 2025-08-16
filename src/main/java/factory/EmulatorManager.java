package factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class EmulatorManager {

  private static Process emulatorProcess;

  public static void startEmulator() throws IOException, InterruptedException {
    System.out.println("🔄 Starting Android Emulator...");

    String emulatorPath = "/Users/gevorg.kocharyan/Library/Android/sdk/emulator/emulator";

    // 1. List available AVDs
    String avdName = getAvdName(emulatorPath);

    System.out.println("📱 Using AVD: " + avdName);

    // 2. Start the emulator
    ProcessBuilder builder = new ProcessBuilder(
        emulatorPath, "-avd", avdName, "-netdelay", "none",
        "-netspeed", "full", "-no-snapshot"
    );
    builder.inheritIO();
    emulatorProcess = builder.start();

    // 3. Wait for the device to be ready
    waitForDevice();

    System.out.println("✅ Emulator started and ready.");
  }

  private static String getAvdName(String emulatorPath) throws IOException, InterruptedException {
    ProcessBuilder listAvdsBuilder = new ProcessBuilder(emulatorPath, "-list-avds");
    Process listAvdsProcess = listAvdsBuilder.start();

    String avdName = null;
    try (BufferedReader reader = new BufferedReader(
        new InputStreamReader(listAvdsProcess.getInputStream(), StandardCharsets.UTF_8))) {
      // Read the first line (first AVD)
      avdName = reader.readLine();

      // Optional: print all available AVDs
      String line;
      while ((line = reader.readLine()) != null) {
        System.out.println(line);
      }
    }

    // Wait for the process to finish
    int exitCode = listAvdsProcess.waitFor();
    if (exitCode != 0) {
      throw new RuntimeException("❌ Failed to list AVDs, process exited with code " + exitCode);
    }

    if (avdName == null || avdName.isEmpty()) {
      throw new RuntimeException("❌ No AVDs found. Please create one in Android Studio AVD Manager.");
    }

    return avdName;
  }

  public static void stopEmulator() throws IOException, InterruptedException {
    System.out.println("🛑 Stopping Android Emulator...");
    Process killProcess = new ProcessBuilder("adb", "emu", "kill").start();
    killProcess.waitFor();
    System.out.println("✅ Emulator stopped.");
  }

  private static void waitForDevice() throws IOException, InterruptedException {
    System.out.println("⏳ Waiting for emulator to fully boot...");

    // Wait for device to be visible
    new ProcessBuilder("adb", "wait-for-device").start().waitFor();

    while (true) {
      Process checkBoot = new ProcessBuilder("adb", "shell", "getprop", "sys.boot_completed").start();
      try (BufferedReader reader = new BufferedReader(new InputStreamReader(checkBoot.getInputStream(), StandardCharsets.UTF_8))) {
        String line = reader.readLine();
        if (line != null && line.trim().equals("1")) {
          System.out.println("📱 Emulator boot completed.");
          break;
        }
      }
      Thread.sleep(3000); // check every 3 seconds
    }

    // Unlock the screen
    new ProcessBuilder("adb", "shell", "input", "keyevent", "82").start();
  }

}
