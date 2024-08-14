package com.example.test.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.test.ui.screen.people.PeopleScreen
import com.example.test.ui.screen.people.PeopleViewModel
import com.example.test.ui.screen.person.PersonScreen
import com.example.test.ui.screen.person.PersonViewModel

@Composable
fun MainNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Destination.PeopleScreen.route,
    ) {
        composable(
            route = Destination.PeopleScreen.route,
            exitTransition = { slideOutHorizontally() + fadeOut() },
            popEnterTransition = { slideInHorizontally() + fadeIn() },
        ) {
            PeopleScreen(
                viewModel = viewModel(factory = PeopleViewModel.factory()),
                onPersonClick = { person ->
                    navController.navigate(
                        route = Destination.PersonScreen.createRoute(personId = person.id)
                    )
                }
            )
        }

        composable(
            route = Destination.PersonScreen.route,
            enterTransition = { slideInHorizontally { it / 2 } + fadeIn() },
            popExitTransition = { slideOutHorizontally { it / 2 } + fadeOut() },
        ) { navEntry ->
            val personId = navEntry.arguments?.getString(PARAM_PERSON_ID) ?: return@composable

            PersonScreen(
                viewModel = viewModel(
                    factory = PersonViewModel.factory(personId)
                ),
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
