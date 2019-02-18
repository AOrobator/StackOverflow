package com.orobator.stackoverflow.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.orobator.stackoverflow.client.models.User
import com.orobator.stackoverflow.client.questions.Question
import com.orobator.stackoverflow.client.questions.QuestionsApi
import com.orobator.stackoverflow.client.questions.QuestionsRepository
import com.orobator.stackoverflow.client.questions.QuestionsResponse
import com.orobator.stackoverflow.interactors.QuestionsInteractor
import com.orobator.stackoverflow.interactors.QuestionsUseCases
import com.orobator.stackoverflow.rx.AppSchedulers
import com.orobator.stackoverflow.view.QuestionsViewState
import io.reactivex.Single
import io.reactivex.schedulers.TestScheduler
import org.amshove.kluent.`should equal`
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

class QuestionsViewModelUnitTest {
  private val testScheduler = TestScheduler()
  private val schedulers = AppSchedulers(testScheduler, testScheduler)
  private lateinit var viewModel: QuestionsViewModel

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  @Test
  fun `Initial state should be empty`() {
    val questionsUseCases = mock<QuestionsUseCases>()
    viewModel = QuestionsViewModel(questionsUseCases, schedulers)

    viewModel.viewState.get() `should equal` QuestionsViewState(false, true, false)
  }

  @Test
  fun `When load starts, view is in loading state`() {
    val questionsUseCases = mock<QuestionsUseCases> {
      on { hotQuestions } doReturn Single.create { listOf<QuestionViewModel>() }
    }

    viewModel = QuestionsViewModel(questionsUseCases, schedulers)
    viewModel.loadQuestions()

    viewModel.viewState.get() `should equal` QuestionsViewState(true, false, false)
  }

  @Test
  fun `On successful load, questions are shown`() {
    val questionsRepository = mock<QuestionsRepository> {
      on { getQuestions(1, 10, QuestionsApi.Order.DESC, QuestionsApi.Sort.HOT) } doReturn Single.fromCallable {
        QuestionsResponse(
          mutableListOf(
            Question(
              listOf("c", "pointers", "struct"),
              User(
                20,
                5101709,
                "registered",
                null,
                "https://i.stack.imgur.com/fkAgH.png?s=128&g=1",
                "Shwig",
                "https://stackoverflow.com/users/5101709/shwig"
              ),
              true,
              16,
              2,
              54729040,
              2,
              1550364803,
              1550363442,
              1550364803,
              54729015,
              "https://stackoverflow.com/questions/54729015/is-it-bad-practice-to-store-a-struct-member-value-in-local-var-with-a-shorter-na",
              "Code broken, pls help"
            )
          ),
          true,
          1000,
          1000
        )
      }
    }

    val questionsUseCases = QuestionsInteractor(questionsRepository, QuestionsInteractor.HtmlParser { it })

    viewModel = QuestionsViewModel(questionsUseCases, schedulers)
    viewModel.loadQuestions()

    testScheduler.triggerActions()

    viewModel.viewState.get() `should equal` QuestionsViewState(false, false, false)
    val observer = mock<Observer<in MutableList<QuestionViewModel>>>()
    viewModel.questionViewModels.observeForever(observer)
    verify(observer).onChanged(
      mutableListOf(QuestionViewModel("Code broken, pls help", 2, listOf("c", "pointers", "struct")))
    )
  }

  @Test
  fun `On loading error, error view shown`() {
    val questionsUseCases = mock<QuestionsUseCases> {
      on { hotQuestions } doReturn Single.error(Throwable())
    }

    viewModel = QuestionsViewModel(questionsUseCases, schedulers)
    viewModel.loadQuestions()

    testScheduler.triggerActions()
    viewModel.viewState.get() `should equal` QuestionsViewState(false, false, true)

    val observer = mock<Observer<in MutableList<QuestionViewModel>>>()
    viewModel.questionViewModels.observeForever(observer)

    verify(observer, never()).onChanged(any())
  }
}