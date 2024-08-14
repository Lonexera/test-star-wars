package com.example.test.ui.screen.person

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.test.di.DependencyProvider
import com.example.test.domain.model.Person
import com.example.test.domain.usecase.GetPersonUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonViewModel(
    private val personId: String,
    private val getPersonUseCase: GetPersonUseCase,
) : ViewModel() {

    sealed interface UiState {
        data object Loading : UiState
        data class PersonData(
            val person: Person
        ) : UiState

        data object Error : UiState
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> get() = _uiState.asStateFlow()

    init {
        fetchPerson()
    }

    private fun fetchPerson() {
        viewModelScope.launch {
            _uiState.update { UiState.Loading }

            getPersonUseCase(personId = personId)
                .onSuccess { person ->
                    _uiState.update {
                        // TODO: research in which cases null value is returned
                        if (person != null) {
                            UiState.PersonData(person = person)
                        } else {
                            UiState.Error
                        }
                    }
                }
                .onFailure {
//                    Log.e("PersonViewModel:fetchPerson", it.stackTraceToString())
                    _uiState.update { UiState.Error }
                }
        }
    }

    companion object {
        fun factory(personId: String) = viewModelFactory {
            initializer {
                PersonViewModel(
                    personId = personId,
                    getPersonUseCase = DependencyProvider.getPersonUseCase,
                )
            }
        }
    }
}
