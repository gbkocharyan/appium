package extensions;

import com.codeborne.selenide.WebDriverRunner;
import com.google.inject.Guice;
import factory.AppiumServerManager;
import factory.DriverFactory;
import factory.EmulatorManager;
import io.appium.java_client.AppiumDriver;
import modules.GuiceModule;
import org.junit.jupiter.api.extension.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UIExtension implements BeforeEachCallback, AfterEachCallback, BeforeAllCallback,
    AfterAllCallback {

  protected AppiumDriver driver;
  private final DriverFactory driverFactory = new DriverFactory();
  private static final Logger LOGGER = LoggerFactory.getLogger(UIExtension.class);


  @Override
  public void afterAll(ExtensionContext context) throws Exception {
    EmulatorManager.stopEmulator();
    AppiumServerManager.stopAppiumServer();
  }

  @Override
  public void afterEach(ExtensionContext context) throws Exception {
    if (driver != null) {
      driver.quit();
      WebDriverRunner.closeWebDriver();
    }
  }

  @Override
  public void beforeAll(ExtensionContext context) throws Exception {
    EmulatorManager.startEmulator();
    AppiumServerManager.startAppiumServer();

  }

  @Override
  public void beforeEach(ExtensionContext context) throws Exception {
    try {
      driver = driverFactory.createDriver();
      WebDriverRunner.setWebDriver(driver);
      context.getTestInstance().ifPresent(testInstance -> {
        Guice.createInjector(new GuiceModule()).injectMembers(testInstance);
      });
    } catch (Exception e) {
      LOGGER.error("An error occurred in beforeEach method.", e);
    }
  }

}
