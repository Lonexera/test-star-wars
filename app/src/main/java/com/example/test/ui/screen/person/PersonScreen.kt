package com.example.test.ui.screen.person

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import com.example.test.R
import com.example.test.domain.model.Person
import com.example.test.ui.common.Loading
import com.example.test.ui.theme.TestStarWarsAppTheme
import kotlinx.coroutines.launch

private const val HERE_CLICK_TAG = "here_click"

@Composable
fun PersonScreen(
    viewModel: PersonViewModel,
    onBackClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    BackHandler(onBack = onBackClick)

    PersonScreen(
        uiState = uiState,
        onBackClick = onBackClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PersonScreen(
    uiState: PersonViewModel.UiState,
    onBackClick: () -> Unit,
) {
    Column(
        modifier = Modifier.systemBarsPadding()
    ) {
        TopAppBar(
            title = { Text(text = "People", color = Color.White) },
            navigationIcon = {
                IconButton(
                    onClick = dropUnlessResumed(block = onBackClick)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_arrow_back_ios),
                        contentDescription = "Arrow back",
                        tint = Color.White,
                    )
                }
            }
        )

        when (uiState) {
            PersonViewModel.UiState.Loading -> LoadingState()
            PersonViewModel.UiState.Error -> ErrorState()
            is PersonViewModel.UiState.PersonData -> PersonDataState(uiState = uiState)
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
            text = "Failed to load person data",
            color = Color.White,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PersonDataState(
    uiState: PersonViewModel.UiState.PersonData,
) {
    val scope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet by remember { mutableStateOf(false) }

    val text = buildClickHereText(personName = uiState.person.name)

    ClickableText(
        modifier = Modifier.padding(all = 20.dp),
        text = text,
        style = MaterialTheme.typography.titleLarge.copy(
            color = Color.White,
        ),
        onClick = { offset ->
            text.getStringAnnotations(tag = HERE_CLICK_TAG, start = offset, end = offset)
                .firstOrNull()
                ?.let {
                    scope.launch {
                        showBottomSheet = true
                    }
                }
        }
    )

    if (showBottomSheet) {
        ModalBottomSheet(
            modifier = Modifier.fillMaxHeight(0.5f),
            onDismissRequest = {
                scope.launch { sheetState.hide() }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet = false
                    }
                }
            },
            sheetState = sheetState,
            containerColor = Color.White,
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = uiState.person.name)
                Text(text = "Homeworld: ${uiState.person.homeworldName}")
            }
        }
    }
}

private fun buildClickHereText(
    personName: String,
): AnnotatedString = buildAnnotatedString {
    append("Click ")
    pushStringAnnotation(tag = HERE_CLICK_TAG, annotation = HERE_CLICK_TAG)
    withStyle(
        SpanStyle(
            textDecoration = TextDecoration.Underline,
            color = Color.Red,
        )
    ) {
        append("here")
    }
    pop()
    append(" to view homeworld for $personName")
}

@Preview
@Composable
private fun PreviewPersonScreen() {
    TestStarWarsAppTheme {
        PersonScreen(
            uiState = PersonViewModel.UiState.PersonData(
                person = Person(
                    id = "0",
                    name = "Some name",
                    height = 0,
                    mass = 0,
                    homeworldName = "some homeworld"
                )
            ),
            onBackClick = {}
        )
    }
}
