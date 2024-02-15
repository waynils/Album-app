package com.wb.ui.compose.atoms

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.wb.component.R
import com.wb.ui.theme.MyAppTheme
import com.wb.ui.utils.LightDarkModeOneDevicePreviews

private const val USER_AGENT = "User-Agent"
private const val USER_AGENT_ANDROID = "Android"

data class AlbumPictureData(val urlPicture: String)

@Composable
fun AlbumPicture(data: AlbumPictureData, modifier: Modifier = Modifier) {

    Image(
        painter = rememberAsyncImagePainter(
            model = ImageRequest.Builder(LocalContext.current)
                .addHeader(USER_AGENT, USER_AGENT_ANDROID)
                .data(data.urlPicture)
                .build(),
            placeholder = painterResource(R.drawable.place_holder_album),
            error = painterResource(R.drawable.place_holder_album)
        ),
        contentDescription = "album picture",
        contentScale = ContentScale.Crop,
        modifier = modifier
            .clip(MaterialTheme.shapes.medium)
    )

}


@LightDarkModeOneDevicePreviews
@Composable
private fun AlbumPicturePreview() {
    MyAppTheme {
        Surface(Modifier.fillMaxSize()) {
            Box(modifier = Modifier) {
                AlbumPicture(data = AlbumPictureData(""), modifier = Modifier.size(100.dp))
            }
        }
    }
}
