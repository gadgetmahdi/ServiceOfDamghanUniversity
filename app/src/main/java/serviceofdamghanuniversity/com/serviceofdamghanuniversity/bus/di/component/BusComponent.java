package serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.di.component;

import javax.inject.Singleton;

import dagger.Component;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.BusClass;
import serviceofdamghanuniversity.com.serviceofdamghanuniversity.bus.di.module.BusModule;

@Singleton
@Component(modules = BusModule.class)
public interface BusComponent {
  void injectBus(BusClass busClass);
}
