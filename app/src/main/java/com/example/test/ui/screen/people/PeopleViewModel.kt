package com.example.test.ui.screen.people

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.test.di.DependencyProvider
import com.example.test.domain.model.Person
import com.example.test.domain.usecase.GetPeopleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PeopleViewModel(
    private val getPeopleUseCase: GetPeopleUseCase,
) : ViewModel() {

    sealed interface UiState {
        data object Loading : UiState

        data class PeopleList(
            val people: List<Person>,
        ) : UiState

        data object Error : UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    init {
        fetchPeople()
    }

    private fun fetchPeople() {
        viewModelScope.launch {
            _uiState.update { UiState.Loading }

            getPeopleUseCase()
                .onSuccess { people ->
                    _uiState.update {
                        UiState.PeopleList(people = people)
                    }
                }
                .onFailure {
//                    Log.e("PeopleViewModel:fetchPeople", it.stackTraceToString())
                    _uiState.update { UiState.Error }
                }
        }
    }

    companion object {
        fun factory() = viewModelFactory {
            initializer {
                PeopleViewModel(
                    getPeopleUseCase = DependencyProvider.getPeopleUseCase,
                )
            }
        }
    }
}
