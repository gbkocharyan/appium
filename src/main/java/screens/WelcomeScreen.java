package screens;

import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

public class WelcomeScreen {

  private final SelenideElement nextButton = $(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(4)"));
  private final SelenideElement skipButton = $(AppiumBy.androidUIAutomator("new UiSelector().text(\"Skip >\")"));

  public WelcomeScreen clickNextButton() {
    nextButton.click();
    return this;
  }

  public void clickSkipButton() {
    skipButton.click();
  }
}
