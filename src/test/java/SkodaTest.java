import static org.testng.AssertJUnit.assertTrue;

import jakarta.inject.Inject;
import org.testng.annotations.Test;
import screens.HomeScreen;

public class SkodaTest extends TestBase {

  @Inject
  HomeScreen homeScreen;

  @Test
  public void checkDillersButton() {
    assertTrue(homeScreen.isDrillersButtonDisplayed());
  }

  @Test
  public void checkSupportButton() {
    assertTrue(homeScreen.isSupportButtonDisplayed());
  }

  @Test
  public void checkServicesButton() {
    assertTrue(homeScreen.isServicesButtonDisplayed());
  }

}
