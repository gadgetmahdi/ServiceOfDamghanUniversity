package serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.di.component;

import javax.inject.Singleton;

import dagger.Component;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.BusPositionClass;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.position.di.module.BusPositionModule;

@Singleton
@Component(modules = BusPositionModule.class)
public interface BusPositionComponent {
  void inject(BusPositionClass BusPositionClass);
}
