package com.example.test.ui.screen.person

import com.example.test.domain.usecase.GetPersonUseCase
import com.example.test.rule.MainDispatcherRule
import com.example.test.ui.screen.person.stub.StubGetPersonUseCase
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test

class PersonViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private fun createViewModel(
        personId: String = "",
        getPersonUseCase: GetPersonUseCase = StubGetPersonUseCase(
            result = Result.success(createPerson()),
        )
    ): PersonViewModel = PersonViewModel(
        personId = personId,
        getPersonUseCase = getPersonUseCase
    )

    @Test
    fun `initial uiState is loading`() {
        val viewModel = createViewModel()

        assertEquals(PersonViewModel.UiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `uiState is PersonData when people loading was successful and data is not null`() =
        runTest {
            val viewModel = createViewModel()

            advanceTimeBy(300L)

            assertTrue(viewModel.uiState.value is PersonViewModel.UiState.PersonData)
        }

    @Test
    fun `uiState is Error when people loading was successful but data is null`() =
        runTest {
            val viewModel = createViewModel(
                getPersonUseCase = StubGetPersonUseCase(
                    result = Result.success(null),
                )
            )

            advanceTimeBy(300L)

            assertEquals(PersonViewModel.UiState.Error, viewModel.uiState.value)
        }

    @Test
    fun `uiState is Error when people loading has failed`() = runTest {
        val viewModel = createViewModel(
            getPersonUseCase = StubGetPersonUseCase(
                result = Result.failure(Throwable()),
            )
        )

        advanceTimeBy(300L)

        assertEquals(PersonViewModel.UiState.Error, viewModel.uiState.value)
    }
}
