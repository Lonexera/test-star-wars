package com.example.test.domain.usecase

import com.example.test.domain.model.Person

interface GetPersonUseCase {
    suspend operator fun invoke(personId: String): Result<Person?>
}
