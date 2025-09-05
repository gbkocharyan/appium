package screens;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import org.openqa.selenium.WebDriver;

public abstract class BaseScreen {

  private WebDriver driver;

  public BaseScreen() {
    this.driver = WebDriverRunner.getWebDriver();
  }

  public String getCurrentActivity() {
    return driver.getPageSource();
  }

  public void click(SelenideElement element) {
    element.click();
  }

  public boolean isDisplayed(SelenideElement element) {
    return element.isDisplayed();
  }
}
