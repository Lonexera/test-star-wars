package com.example.test.ui.screen.person.stub

import com.example.test.domain.model.Person
import com.example.test.domain.usecase.GetPersonUseCase
import kotlinx.coroutines.delay

class StubGetPersonUseCase(
    private val result: Result<Person?>,
    private val delay: Long = 200L,
) : GetPersonUseCase {

    override suspend fun invoke(personId: String): Result<Person?> {
        delay(delay)
        return result
    }
}
