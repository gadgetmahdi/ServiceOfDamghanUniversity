package serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.di.component;

import javax.inject.Singleton;

import dagger.Component;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.splash.SplashFragment;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.di.module.TokenModule;

@Singleton
@Component(modules = TokenModule.class)
public interface TokenComponent {
  void inject(SplashFragment splashFragment);
}
