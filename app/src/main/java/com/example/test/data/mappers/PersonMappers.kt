package com.example.test.data.mappers

import com.example.test.apollo.GetPeopleQuery
import com.example.test.apollo.GetPersonQuery
import com.example.test.domain.model.Person

fun GetPeopleQuery.Person.toDomain(): Person =
    Person(
        id = id,
        name = name.orEmpty(),
        height = height ?: 0,
        mass = mass?.toInt() ?: 0,
        homeworldName = homeworld?.name.orEmpty(),
    )

fun GetPersonQuery.Person.toDomain(): Person =
    Person(
        id = id,
        name = name.orEmpty(),
        height = height ?: 0,
        mass = mass?.toInt() ?: 0,
        homeworldName = homeworld?.name.orEmpty(),
    )
