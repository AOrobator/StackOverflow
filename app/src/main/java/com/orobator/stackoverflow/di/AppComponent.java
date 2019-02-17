package com.orobator.stackoverflow.di;

import com.orobator.stackoverflow.StackOverflowApplication;
import dagger.BindsInstance;
import dagger.Component;
import dagger.android.support.AndroidSupportInjectionModule;
import javax.inject.Singleton;

@Singleton
@Component(modules = {
    AndroidSupportInjectionModule.class,
    AppModule.class,
    BuildersModule.class
})
public interface AppComponent {
  void inject(StackOverflowApplication app);

  @Component.Builder
  interface Builder {
    @BindsInstance
    Builder application(StackOverflowApplication application);

    AppComponent build();
  }
}
