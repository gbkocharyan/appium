package factory;

import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import java.net.URL;

public class DriverFactory {

  public AppiumDriver setUp() throws Exception {
    String runType = System.getProperty("runType").toLowerCase();

    DesiredCapabilities capabilities = new DesiredCapabilities();
    capabilities.setCapability("platformName", "Android");
    capabilities.setCapability("appium:automationName", "UiAutomator2");
    capabilities.setCapability("appium:autoGrantPermissions", true);
    capabilities.setCapability("appium:noReset", false);
    capabilities.setCapability("appium:uiautomator2ServerInstallTimeout", 180000);
    capabilities.setCapability("appium:adbExecTimeout", 180000);
    capabilities.setCapability("appium:appPackage", "ru.skoda.service");

    URL remoteUrl;
    if ("remote".equals(runType)) {
      capabilities.setCapability("appium:deviceName", "emulator-5554");
      capabilities.setCapability("appium:app", "/root/tmp/skoda.apk");
      capabilities.setCapability("noSign", true);
      remoteUrl = new URL("http://45.132.17.22:4723/wd/hub");
    } else {
      capabilities.setCapability("appium:deviceName", "emulator-5554");
      String appPath = System.getProperty("user.dir") + "/src/test/resources/skoda.apk";
      capabilities.setCapability("appium:app", appPath);
      remoteUrl = new URL("http://127.0.0.1:4723");
    }
    return new AppiumDriver(remoteUrl, capabilities);
  }

  public AppiumDriver getDriver() {
    return (AppiumDriver) WebDriverRunner.getWebDriver();
  }
}
