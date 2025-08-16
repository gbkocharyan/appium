
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import extensions.UIExtension;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import screens.HomeScreen;
import screens.WelcomeScreen;

@ExtendWith(UIExtension.class)
public class AndyTest {

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
