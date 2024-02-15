package com.wb.ui.compose.molecules.errorstate

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.wb.component.R
import com.wb.ui.theme.MyAppTheme
import com.wb.ui.utils.LightDarkModeOneDevicePreviews
import com.wb.ui.utils.UIString
import com.wb.ui.utils.toUIString

private val iconSize = 64.dp

data class ErrorStateData(
    val title: UIString,
    val subtitle: UIString,
    val buttonLabel: UIString,
)

@Composable
fun ErrorState(
    data: ErrorStateData,
    onErrorButtonAction: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {

        Icon(
            painter = painterResource(id = R.drawable.place_holder_album),
            contentDescription = data.title.buildString(),
            tint = MaterialTheme.colors.onBackground,
            modifier = Modifier.size(iconSize)
        )


        Text(
            style = MaterialTheme.typography.h3,
            color = MaterialTheme.colors.onBackground,
            text = data.title.buildString(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            textAlign = TextAlign.Center,
        )


        Text(
            style = MaterialTheme.typography.body2,
            color = MaterialTheme.colors.onBackground,
            text = data.subtitle.buildString(),
            overflow = TextOverflow.Ellipsis,
            maxLines = 2,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(
            onClick = onErrorButtonAction
        ) {
            Text(
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground,
                text = data.buttonLabel.buildString(),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1,
                textAlign = TextAlign.Center,
            )
        }
    }
}


@LightDarkModeOneDevicePreviews
@Composable
private fun ErrorStatePreview() {
    MyAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                ErrorState(
                    data = ErrorStateData(
                        title = "Error".toUIString(),
                        subtitle = "an error occurred while processing your request".toUIString(),
                        buttonLabel = "Retry".toUIString(),
                    ),
                    onErrorButtonAction = {/*PREVIEW*/}
                )
            }
        }
    }
}