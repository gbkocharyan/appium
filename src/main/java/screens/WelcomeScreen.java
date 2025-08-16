package screens;

import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import io.appium.java_client.AppiumBy;

public class WelcomeScreen {

  private final SelenideElement nextButton = $(AppiumBy.androidUIAutomator("new UiSelector().className(\"android.view.ViewGroup\").instance(4)"));
  private final SelenideElement skipButton = $(AppiumBy.androidUIAutomator("new UiSelector().text(\"Skip >\")"));
  private final SelenideElement welcomeScreenText = $(AppiumBy.androidUIAutomator("new UiSelector().text(\"Chat to improve your English\")"));

  public WelcomeScreen clickOnNextButton() {
    nextButton.shouldBe(Condition.visible).click();
    return this;
  }

  public void clickOnSkipButton() {
    skipButton.click();
  }

  public String getWelcomeScreenText() {
    return welcomeScreenText.getText();
  }
}
