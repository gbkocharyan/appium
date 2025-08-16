package screens;

import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.WebDriver;

public class HomeScreen {

  public HomeScreen() {
  }

  private final SelenideElement dillers = $(AppiumBy.id("imageCardThreeHomeScreen"));
  private final SelenideElement fillProfile = $(AppiumBy.id("mageCardFourHomeScreen"));
  private final SelenideElement servicesButton = $(AppiumBy.id("mageCardOneHomeScreen"));
  private final SelenideElement supportButton = $(AppiumBy.id("imageCardTwoHomeScreen"));

  public boolean isServicesButtonDisplayed() {
    return servicesButton.isDisplayed();
  }

  public boolean isSupportButtonDisplayed() {
    return supportButton.isDisplayed();
  }

  public boolean isDrillersButtonDisplayed() {
    return dillers.isDisplayed();
  }

  public boolean isFillProfileButtonDisplayed() {
    return fillProfile.isDisplayed();
  }
}
