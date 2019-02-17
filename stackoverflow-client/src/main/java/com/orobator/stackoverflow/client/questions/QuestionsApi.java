package com.orobator.stackoverflow.client.questions;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface QuestionsApi {
    String STACK_OVERFLOW_SITE = "stackoverflow";

    @GET("v2.2/questions")
    Single<QuestionsResponse> getQuestions(
            @Query("page") int page,
            @Query("pagesize") int pageSize,
            @Query("order") String order,
            @Query("sort") String sort,
            @Query("site") String site
    );

    enum Order {
        ASC("asc"),
        DESC("desc");

        public final String val;

        Order(String val) {
            this.val = val;
        }
    }

    enum Sort {
        ACTIVITY("activity"),
        VOTES("votes"),
        CREATION("creation"),
        HOT("hot"),
        WEEK("week"),
        MONTH("month");

        public final String val;

        Sort(String val) {
            this.val = val;
        }
    }

}
