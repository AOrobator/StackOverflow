package com.orobator.stackoverflow.di;

import com.orobator.stackoverflow.view.MainActivity;
import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class BuildersModule {

  @ContributesAndroidInjector
  abstract MainActivity bindMainActivity();
}
