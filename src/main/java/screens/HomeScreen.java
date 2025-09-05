package screens;

import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

public class HomeScreen extends BaseScreen {

  private SelenideElement dillers = $(AppiumBy.id("imageCardThreeHomeScreen"));
  private SelenideElement fillProfile = $(AppiumBy.id("mageCardFourHomeScreen"));
  private SelenideElement servicesButton = $(AppiumBy.id("mageCardOneHomeScreen"));
  private SelenideElement supportButton = $(AppiumBy.id("imageCardTwoHomeScreen"));

  public boolean isServicesButtonDisplayed() {
    return isDisplayed(servicesButton);
  }

  public boolean isSupportButtonDisplayed() {
    return isDisplayed(supportButton);
  }

  public boolean isDrillersButtonDisplayed() {
    return isDisplayed(dillers);
  }

  public boolean isFillProfileButtonDisplayed() {
    return isDisplayed(fillProfile);
  }
}
