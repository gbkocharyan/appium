import com.codeborne.selenide.WebDriverRunner;
import com.google.inject.Guice;
import factory.AppiumServerManager;
import factory.DriverFactory;
import factory.EmulatorManager;
import io.appium.java_client.AppiumDriver;
import modules.GuiceModule;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

public class TestBase {

  protected AppiumDriver driver;
  private DriverFactory driverFactory = new DriverFactory();

  @BeforeSuite
  public void beforeSuite() throws Exception {
    EmulatorManager.startEmulator();
    AppiumServerManager.startAppiumServer();
  }

  @BeforeMethod
  public void beforeEach() {
    try {
      driver = driverFactory.createDriver();
      WebDriverRunner.setWebDriver(driver);
      Guice.createInjector(new GuiceModule())
          .injectMembers(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @AfterMethod
  public void afterEach() {
    if (driver != null) {
      driver.quit();
      WebDriverRunner.closeWebDriver();
    }
  }

  @AfterSuite
  public void afterSuite() throws Exception {
    AppiumServerManager.stopAppiumServer();
    EmulatorManager.stopEmulator();
  }
}
