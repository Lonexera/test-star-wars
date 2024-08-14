package com.example.test.data.usecase

import com.apollographql.apollo.ApolloClient
import com.example.test.apollo.GetPeopleQuery
import com.example.test.data.mappers.toDomain
import com.example.test.domain.model.Person
import com.example.test.domain.usecase.GetPeopleUseCase

class GetPeopleUseCaseImpl(
    private val apolloClient: ApolloClient,
) : GetPeopleUseCase {

    override suspend operator fun invoke(): Result<List<Person>> {
        return runCatching {
            apolloClient
                .query(GetPeopleQuery())
                .execute()
        }
            .map { data ->
                data.data?.allPeople
                    ?.people
                    .orEmpty()
                    .mapNotNull { it?.toDomain() }
            }
    }
}
