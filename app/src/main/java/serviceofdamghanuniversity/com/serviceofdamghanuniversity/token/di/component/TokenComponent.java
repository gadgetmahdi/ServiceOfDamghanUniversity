package serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.di.component;

import javax.inject.Singleton;

import dagger.Component;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.TokenClass;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.token.di.module.TokenModule;

@Singleton
@Component(modules = TokenModule.class)
public interface TokenComponent {
  void inject(TokenClass tokenClass);
}
