package com.example.test.ui.screen.people

import com.example.test.domain.usecase.GetPeopleUseCase
import com.example.test.rule.MainDispatcherRule
import com.example.test.ui.screen.people.stub.StubGetPeopleUseCase
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class PeopleViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createViewModel(
        getPeopleUseCase: GetPeopleUseCase = StubGetPeopleUseCase(
            result = Result.success(emptyList()),
        )
    ): PeopleViewModel = PeopleViewModel(
        getPeopleUseCase = getPeopleUseCase
    )

    @Test
    fun `initial uiState is loading`() {
        val viewModel = createViewModel()

        assertEquals(PeopleViewModel.UiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `uiState is PeopleList when people loading was successful`() = runTest {
        val viewModel = createViewModel()

        advanceTimeBy(300L)

        assertTrue(viewModel.uiState.value is PeopleViewModel.UiState.PeopleList)
    }

    @Test
    fun `uiState is Error when people loading has failed`() = runTest {
        val viewModel = createViewModel(
            getPeopleUseCase = StubGetPeopleUseCase(
                result = Result.failure(Throwable()),
            )
        )

        advanceTimeBy(300L)

        assertEquals(PeopleViewModel.UiState.Error, viewModel.uiState.value)
    }
}
