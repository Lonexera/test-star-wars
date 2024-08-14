package com.example.test.ui.navigation

const val PARAM_PERSON_ID = "person_id"

private const val PEOPLE_SCREEN_ROUTE = "people_screen"
private const val PERSON_SCREEN_ROUTE = "person_screen/{$PARAM_PERSON_ID}"

sealed interface Destination {
    val route: String

    data object PeopleScreen : Destination {
        override val route = PEOPLE_SCREEN_ROUTE
    }

    data object PersonScreen : Destination {
        override val route = PERSON_SCREEN_ROUTE

        fun createRoute(personId: String): String =
            route.replace("{$PARAM_PERSON_ID}", personId)
    }
}
