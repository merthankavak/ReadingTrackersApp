package com.mkavaktech.readingtrackers.core.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mkavaktech.readingtrackers.core.init.model.CustomBookModel

@Preview
@Composable
@ExperimentalMaterial3Api
fun BookInfoCard(
    modifier: Modifier = Modifier,
    book: CustomBookModel = CustomBookModel("SAD", "Book Title", "Authors Name"),
    onPress: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val resources = context.resources
    val displayMetrics = resources.displayMetrics
    val screenWidth = displayMetrics.widthPixels / displayMetrics.density
    val spacing = 5.dp
    val isStartedReading = remember { mutableStateOf(false) }
    Card(
        modifier = modifier
            .padding(5.dp)
            .clickable { onPress.invoke(book.title!!) }
            .width(150.dp)
            .height(200.dp),
        shape = RoundedCornerShape(30.dp),
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        Column(
            modifier = modifier
                .padding(15.dp)
                .width(screenWidth.dp - (spacing * 2)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Image(
                    modifier = modifier
                        .width(50.dp)
                        .height(70.dp)
                        .padding(5.dp),
                    painter = rememberAsyncImagePainter(model = book.photoUrl),
                    contentDescription = "Book Img",
                    contentScale = ContentScale.Fit
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.FavoriteBorder,
                        contentDescription = "Fav Icon",
                    )
                    BookScoreCard(score = book.rating!!)
                }
            }
            Text(
                text = book.title!!,
                style = MaterialTheme.typography.bodySmall,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
            Text(
                text = "${book.authors}",
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodySmall
            )

        }
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Start,
            modifier = modifier.fillMaxSize(1f)
        ) {
            isStartedReading.value = book.startedReading != null
            ReadingStatusCard(
                modifier,
                label = if (isStartedReading.value) "Reading" else "Not Yet",
                radius = 70
            )
        }
    }


}
