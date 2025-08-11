
import static org.junit.jupiter.api.Assertions.assertTrue;

import extensions.UIExtension;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import screens.HomeScreen;

@ExtendWith(UIExtension.class)
public class SkodaTest {

  @Inject
  HomeScreen homeScreen;

  @Test
  public void drillersButtonTest() {
    assertTrue(homeScreen.isDrillersButtonDisplayed());
  }

}
