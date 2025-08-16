
import static org.junit.jupiter.api.Assertions.assertTrue;

import extensions.UIExtension;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import screens.WelcomeScreen;

@ExtendWith(UIExtension.class)
public class AndyTest {

  @Inject
  WelcomeScreen welcomeScreen;

  @Test
  public void drillersButtonTest() {
    welcomeScreen
        .clickNextButton()
        .clickNextButton()
        .clickSkipButton();
  }

}
