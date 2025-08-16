package factory;

import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

public class DriverFactory {

  private static final String REMOTE_DEVICE_NAME = "emulator-5554";
  private static final String LOCAL_APPIUM_URL = "http://127.0.0.1:4723";
  private static final String REMOTE_APPIUM_URL = "http://45.132.17.22:4723/wd/hub";

  public AppiumDriver createDriver() throws Exception {
    String executionMode = System.getProperty("runType").toLowerCase();

    DesiredCapabilities capabilities = createCapabilities();
    URL remoteUrl;

    if ("remote".equals(executionMode)) {
      capabilities.setCapability("appium:app", "/root/tmp/skoda.apk");
      remoteUrl = new URL(REMOTE_APPIUM_URL);
    } else {
      capabilities.setCapability("appium:app", getAppPath());
      remoteUrl = new URL(LOCAL_APPIUM_URL);
    }

    return new AppiumDriver(remoteUrl, capabilities);
  }

  private DesiredCapabilities createCapabilities() {
    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("appium:automationName", "UiAutomator2");
    capabilities.setCapability("appium:autoGrantPermissions", true);
    capabilities.setCapability("appium:noReset", false);
    capabilities.setCapability("appium:uiautomator2ServerInstallTimeout", 180000);
    capabilities.setCapability("appium:adbExecTimeout", 180000);
    capabilities.setCapability("appium:deviceName", REMOTE_DEVICE_NAME);
    return capabilities;
  }

  private String getAppPath() {
    return System.getProperty("user.dir") + "/src/test/resources/skoda.apk";
  }

}
