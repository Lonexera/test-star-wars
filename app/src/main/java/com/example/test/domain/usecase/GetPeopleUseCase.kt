package com.example.test.domain.usecase

import com.example.test.domain.model.Person

interface GetPeopleUseCase {
    suspend operator fun invoke(): Result<List<Person>>
}
