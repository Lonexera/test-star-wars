package com.example.test.di

import com.apollographql.apollo.ApolloClient
import com.example.test.BuildConfig
import com.example.test.data.usecase.GetPeopleUseCaseImpl
import com.example.test.data.usecase.GetPersonUseCaseImpl

object DependencyProvider {

    private val apolloClient by lazy {
        ApolloClient.Builder()
            .serverUrl(BuildConfig.BASE_URL)
            .build()
    }

    val getPeopleUseCase by lazy {
        GetPeopleUseCaseImpl(apolloClient)
    }

    val getPersonUseCase by lazy {
        GetPersonUseCaseImpl(apolloClient)
    }
}
