package modules;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import screens.HomeScreen;

public class GuiceModule extends AbstractModule {

  @Provides
  @Singleton
  public HomeScreen getHomeScreen() {
    return new HomeScreen();
  }

}
