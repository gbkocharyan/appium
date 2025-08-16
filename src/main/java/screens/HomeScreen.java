package screens;

import static com.codeborne.selenide.appium.SelenideAppium.$;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import io.appium.java_client.AppiumBy;

public class HomeScreen {

  private final SelenideElement exerciseSection = $(AppiumBy.androidUIAutomator("new UiSelector().text(\"Exercise\")"));
  private final SelenideElement grammarSection = $(AppiumBy.androidUIAutomator("new UiSelector().text(\"Grammar\")"));
  private final SelenideElement statsSection = $(AppiumBy.androidUIAutomator("new UiSelector().text(\"Stats\")"));

  public String getExerciseSectionText() {
    return exerciseSection.getText();
  }

  public boolean isGrammarSectionDisplayed() {
    return grammarSection.shouldBe(Condition.visible).isDisplayed();
  }

  public boolean isStatsSectionDisplayed() {
    return statsSection.shouldBe(Condition.visible).isDisplayed();
  }

}
