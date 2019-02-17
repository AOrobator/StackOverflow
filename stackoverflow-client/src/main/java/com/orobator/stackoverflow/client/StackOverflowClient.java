package com.orobator.stackoverflow.client;

import com.orobator.stackoverflow.client.questions.QuestionsApi;
import com.orobator.stackoverflow.client.questions.QuestionsApi.Order;
import com.orobator.stackoverflow.client.questions.QuestionsApi.Sort;
import com.orobator.stackoverflow.client.questions.QuestionsResponse;
import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class StackOverflowClient {
    private Retrofit retrofit;
    private QuestionsApi questionsApi;

    public StackOverflowClient(String baseUrl, HttpLoggingInterceptor httpLoggingInterceptor) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(client)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        questionsApi = retrofit.create(QuestionsApi.class);
    }

    public Single<QuestionsResponse> getQuestions(
            int page,
            int pageSize,
            Order order,
            Sort sort
    ) {
        return questionsApi.getQuestions(page, pageSize, order.val, sort.val, QuestionsApi.STACK_OVERFLOW_SITE);
    }
}
