package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import screens.HomeScreen;
import screens.WelcomeScreen;

public class GuiceModule extends AbstractModule {

  @Provides
  @Singleton
  public HomeScreen getHomeScreen() {
    return new HomeScreen();
  }

  @Provides
  @Singleton
  public WelcomeScreen getWelcomeScreen() {
    return new WelcomeScreen();
  }

}
