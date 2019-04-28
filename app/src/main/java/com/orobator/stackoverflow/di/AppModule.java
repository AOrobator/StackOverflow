package com.orobator.stackoverflow.di;

import android.util.Log;
import androidx.core.text.HtmlCompat;
import com.orobator.stackoverflow.client.questions.repository.QuestionsApi;
import com.orobator.stackoverflow.client.questions.repository.QuestionsDownloader;
import com.orobator.stackoverflow.client.questions.repository.QuestionsRepository;
import com.orobator.stackoverflow.interactors.QuestionsDataSourceFactory;
import com.orobator.stackoverflow.interactors.QuestionsInteractor;
import com.orobator.stackoverflow.interactors.QuestionsUseCases;
import com.orobator.stackoverflow.rx.AppSchedulers;
import com.orobator.stackoverflow.viewmodel.QuestionsViewModelFactory;
import dagger.Module;
import dagger.Provides;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.inject.Singleton;

@Module
public class AppModule {
  @Provides
  public AppSchedulers provideAppSchedulers() {
    return new AppSchedulers(AndroidSchedulers.mainThread(), Schedulers.io());
  }

  @Provides
  @Singleton
  public OkHttpClient provideOkHttpClient() {
    return new OkHttpClient.Builder()
        .addInterceptor(
            new HttpLoggingInterceptor(message -> Log.d("Retrofit", message))
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        )
        .build();
  }

  @Provides
  @Singleton
  public Retrofit provideRetrofit(OkHttpClient client) {
    return new Retrofit.Builder()
        .baseUrl("https://api.stackexchange.com")
        .client(client)
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build();
  }

  @Provides
  public QuestionsApi provideQuestionsApi(Retrofit retrofit) {
    return retrofit.create(QuestionsApi.class);
  }

  @Provides
  public QuestionsRepository provideQuestionsRepository(QuestionsApi questionsApi) {
    return new QuestionsDownloader(questionsApi);
  }

    @Provides
    public QuestionsDataSourceFactory provideQuestionsDataSourceFactory(
            QuestionsRepository repository,
            AppSchedulers schedulers
    ) {
        return new QuestionsDataSourceFactory(repository, schedulers);
    }

  @Provides
  public QuestionsUseCases provideQuestionsUseCases(QuestionsRepository repository) {
    return new QuestionsInteractor(
            repository,
            s -> HtmlCompat.fromHtml(s, HtmlCompat.FROM_HTML_MODE_LEGACY).toString());
  }

  @Provides
  public QuestionsViewModelFactory provideQuestionsViewModelFactory(
      QuestionsUseCases useCases,
      AppSchedulers schedulers
  ) {
    return new QuestionsViewModelFactory(useCases, schedulers);
  }
}
