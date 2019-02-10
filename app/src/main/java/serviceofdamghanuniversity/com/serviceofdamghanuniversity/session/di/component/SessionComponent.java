package serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.di.component;

import javax.inject.Singleton;

import dagger.Component;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.SessionClass;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.session.di.module.SessionModule;

@Singleton
@Component(modules = SessionModule.class)
public interface SessionComponent {
  void inejct(SessionClass sessionClass);
}
