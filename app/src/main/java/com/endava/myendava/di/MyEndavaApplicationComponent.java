package com.endava.myendava.di;

import com.endava.myendava.app.MyEndavaApplication;

import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {MyEndavaApplicationModule.class})
public interface MyEndavaApplicationComponent {

    void inject(MyEndavaApplication application);

    @Component.Builder
    interface Builder {
        MyEndavaApplicationComponent.Builder myEndavaAppModule(MyEndavaApplicationModule module);

        MyEndavaApplicationComponent build();
    }
}