package com.orobator.stackoverflow.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.never
import com.nhaarman.mockitokotlin2.verify
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
    val questionsUseCases = mock<QuestionsUseCases> {
      on { hotQuestions } doReturn Single.fromCallable {
        listOf(QuestionViewModel("Code broken, pls help", -1, listOf("code")))
      }
    }

    viewModel = QuestionsViewModel(questionsUseCases, schedulers)
    viewModel.loadQuestions()

    testScheduler.triggerActions()

    viewModel.viewState.get() `should equal` QuestionsViewState(false, false, false)
    val observer = mock<Observer<in MutableList<QuestionViewModel>>>()
    viewModel.questionViewModels.observeForever(observer)
    verify(observer).onChanged(
        mutableListOf(QuestionViewModel("Code broken, pls help", -1, listOf("code")))
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