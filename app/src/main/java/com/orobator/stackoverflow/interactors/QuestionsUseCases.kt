package com.orobator.stackoverflow.interactors

import com.orobator.stackoverflow.viewmodel.QuestionViewModel
import io.reactivex.Single

interface QuestionsUseCases {
    fun getHotQuestions(page: Int, pageSize: Int): Single<List<QuestionViewModel>>
}
