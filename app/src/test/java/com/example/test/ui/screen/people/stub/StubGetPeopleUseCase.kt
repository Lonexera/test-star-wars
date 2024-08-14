package com.example.test.ui.screen.people.stub

import com.example.test.domain.model.Person
import com.example.test.domain.usecase.GetPeopleUseCase
import kotlinx.coroutines.delay

class StubGetPeopleUseCase(
    private val result: Result<List<Person>>,
    private val delay: Long = 200L,
) : GetPeopleUseCase {

    override suspend fun invoke(): Result<List<Person>> {
        delay(delay)
        return result
    }
}
