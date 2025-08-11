package factory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class EmulatorManager {

  public static void startEmulator() throws IOException, InterruptedException {
    System.out.println("🔄 Starting Android Emulator...");

    String emulatorPath = "/Users/gevorg.kocharyan/Library/Android/sdk/emulator/emulator";

    // 1. List available AVDs
    ProcessBuilder listAvdsBuilder = new ProcessBuilder(emulatorPath, "-list-avds");
    Process listAvdsProcess = listAvdsBuilder.start();

    java.io.BufferedReader reader = new java.io.BufferedReader(
        new java.io.InputStreamReader(listAvdsProcess.getInputStream())
    );

    String avdName = reader.readLine(); // Get first AVD
    listAvdsProcess.waitFor();

    if (avdName == null || avdName.isEmpty()) {
      throw new RuntimeException("❌ No AVDs found. Please create one in Android Studio AVD Manager.");
    }

    System.out.println("📱 Using AVD: " + avdName);

    // 2. Start the emulator
    ProcessBuilder builder = new ProcessBuilder(
        emulatorPath, "-avd", avdName, "-netdelay", "none",
        "-netspeed", "full", "-no-snapshot"
    );
    builder.inheritIO();
    Process emulatorProcess = builder.start();

    // 3. Wait for the device to be ready
    waitForDevice();

    System.out.println("✅ Emulator started and ready.");
  }

  public static void stopEmulator() throws IOException, InterruptedException {
    System.out.println("🛑 Stopping Android Emulator...");
    Process killProcess = new ProcessBuilder("adb", "emu", "kill").start();
    killProcess.waitFor();
    System.out.println("✅ Emulator stopped.");
  }

  private static void waitForDevice() throws IOException, InterruptedException {
    String adbPath = "/Users/gevorg.kocharyan/Library/Android/sdk/platform-tools/adb";
    System.out.println("⏳ Waiting for emulator to fully boot...");

    // Wait for device to be visible
    new ProcessBuilder(adbPath, "wait-for-device").start().waitFor();

    while (true) {
      Process checkBoot = new ProcessBuilder(adbPath, "shell", "getprop", "sys.boot_completed").start();
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
    new ProcessBuilder(adbPath, "shell", "input", "keyevent", "82").start();
  }

}
