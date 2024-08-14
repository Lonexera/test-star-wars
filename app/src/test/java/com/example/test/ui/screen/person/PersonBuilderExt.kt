package com.example.test.ui.screen.person

import com.example.test.domain.model.Person

fun createPerson(
    id: String = "",
    name: String = "",
    height: Int = 0,
    mass: Int = 0,
    homeworldName: String = ""
): Person =
    Person(
        id = id,
        name = name,
        height = height,
        mass = mass,
        homeworldName = homeworldName,
    )
