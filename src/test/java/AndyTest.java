import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import jakarta.inject.Inject;
import org.testng.annotations.Test;
import screens.HomeScreen;
import screens.WelcomeScreen;

public class AndyTest extends TestBase {

  @Inject
  WelcomeScreen welcomeScreen;

  @Inject
  HomeScreen homeScreen;

  @Test
  public void checkWelcomeScreenText() {
    assertEquals("Chat to improve your English", welcomeScreen.getWelcomeScreenText(),
        "Welcome screen text is incorrect");
  }

  @Test
  public void checkExerciseSectionVisibility() {
    welcomeScreen
        .clickOnNextButton()
        .clickOnNextButton()
        .clickOnSkipButton();
    assertEquals("Exercise", homeScreen.getExerciseSectionText(),
        "Exercise section name is incorrect or missing");
  }

  @Test
  public void checkGrammarSectionVisibility() {
    welcomeScreen
        .clickOnNextButton()
        .clickOnNextButton()
        .clickOnSkipButton();
    assertTrue(homeScreen.isGrammarSectionDisplayed(), "Grammar section is missing");
  }

  @Test
  public void checkStatsSectionVisibility() {
    welcomeScreen
        .clickOnNextButton()
        .clickOnNextButton()
        .clickOnSkipButton();
    assertTrue(homeScreen.isStatsSectionDisplayed(), "Stats section is missing");
  }

}
