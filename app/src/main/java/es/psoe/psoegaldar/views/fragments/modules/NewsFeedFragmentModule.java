package es.psoe.psoegaldar.views.fragments.modules;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import es.psoe.presenters.NewsFeedPresenter;
import es.psoe.presenters.interfaces.NewsFeedPresenterInterface;
import es.psoe.psoegaldar.views.fragments.NewsFeedFragment;

/**
 * Created by exalu_000 on 29/03/2015.
 */
@Module(injects = NewsFeedFragment.class)
public class NewsFeedFragmentModule {
    NewsFeedPresenterInterface newsFeedFragment;
    public NewsFeedFragmentModule(){}

    public NewsFeedFragmentModule(NewsFeedPresenterInterface newsFeedFragment) {
        this.newsFeedFragment = newsFeedFragment;
    }

     @Provides @Singleton public NewsFeedPresenter provideNewsFeedPresenter(){
         return new NewsFeedPresenter(newsFeedFragment);
     }
}
