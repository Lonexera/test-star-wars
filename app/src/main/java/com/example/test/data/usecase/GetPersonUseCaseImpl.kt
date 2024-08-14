package com.example.test.data.usecase

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Optional
import com.example.test.apollo.GetPersonQuery
import com.example.test.data.mappers.toDomain
import com.example.test.domain.model.Person
import com.example.test.domain.usecase.GetPersonUseCase

class GetPersonUseCaseImpl(
    private val apolloClient: ApolloClient,
) : GetPersonUseCase {

    override suspend operator fun invoke(
        personId: String,
    ): Result<Person?> {
        return runCatching {
            apolloClient
                .query(
                    GetPersonQuery(
                        personId = Optional.present(personId)
                    )
                )
                .execute()
        }
            .map { data ->
                data.data?.person?.toDomain()
            }
    }
}
