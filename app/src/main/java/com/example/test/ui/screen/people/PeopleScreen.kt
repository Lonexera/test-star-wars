package com.example.test.ui.screen.people

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.test.R
import com.example.test.domain.model.Person
import com.example.test.ui.common.Loading

@Composable
fun PeopleScreen(
    viewModel: PeopleViewModel,
    onPersonClick: (Person) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PeopleScreen(
        uiState = uiState,
        onPersonClick = onPersonClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PeopleScreen(
    uiState: PeopleViewModel.UiState,
    onPersonClick: (Person) -> Unit,
) {
    Column(
        modifier = Modifier.systemBarsPadding()
    ) {
        TopAppBar(
            title = { Text(text = "People", color = Color.White) }
        )

        when (uiState) {
            PeopleViewModel.UiState.Loading -> LoadingState()
            PeopleViewModel.UiState.Error -> ErrorState()
            is PeopleViewModel.UiState.PeopleList -> PeopleListState(
                uiState = uiState,
                onPersonClick = onPersonClick,
            )
        }
    }
}

@Composable
private fun LoadingState() {
    Box(modifier = Modifier.fillMaxSize()) {
        Loading(
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Composable
private fun ErrorState() {
    Box(modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = "Failed to load people list",
            color = Color.White,
        )
    }
}

@Composable
private fun PeopleListState(
    uiState: PeopleViewModel.UiState.PeopleList,
    onPersonClick: (Person) -> Unit,
) {
    LazyColumn(
        contentPadding = PaddingValues(bottom = 32.dp)
    ) {
        items(
            items = uiState.people,
            key = { it.id }
        ) { person ->
            HorizontalDivider()
            PersonItem(
                person = person,
                onClick = { onPersonClick(person) }
            )
        }
    }
}

@Composable
private fun PersonItem(
    person: Person,
    onClick: () -> Unit,
) {
    ListItem(
        modifier = Modifier.clickable(onClick = onClick),
        headlineContent = {
            Text(text = person.name, color = Color.White)
        },
        supportingContent = {
            Column {
                Text(
                    text = "Height: ${person.height}",
                    color = Color.White.copy(alpha = 0.8f)
                )
                Text(
                    text = "Mass: ${person.mass}",
                    color = Color.White.copy(alpha = 0.8f)
                )
            }
        },
        trailingContent = {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_forward_ios),
                contentDescription = "Arrow forward",
                tint = Color.White,
            )
        }
    )
}
