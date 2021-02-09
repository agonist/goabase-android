package com.onionsquare.goabase.feature

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.onionsquare.goabase.theme.Mustard
import com.onionsquare.goabase.theme.PurpleDark

@Composable
fun RetryView(
        modifier: Modifier = Modifier,
        message: String,
        onRetryClicked: () -> Unit
) {
    Column(
            modifier = modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
    ) {
        Text(
                text = message,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                        top = 15.dp,
                        bottom = 20.dp
                )
        )

        Button(onClick = { onRetryClicked() }) {
            Text(text = "retry")
        }
    }
}

@Composable
fun CircularLoader() {
    Box(
            modifier = Modifier
                    .fillMaxSize()
                    .background(PurpleDark),
    ) {
        CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center), color = Mustard
        )
    }
}

@Composable
fun SimpleTitleToolbar(title: String) {
    TopAppBar(
            backgroundColor = MaterialTheme.colors.secondary,
            title = {
                Text(text = title)
            }
    )
}