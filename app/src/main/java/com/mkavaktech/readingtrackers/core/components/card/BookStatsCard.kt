package com.mkavaktech.readingtrackers.core.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mkavaktech.readingtrackers.core.constants.app.AppConstants
import com.mkavaktech.readingtrackers.core.init.model.CustomBookModel
import com.mkavaktech.readingtrackers.core.utils.formatDate

@ExperimentalMaterial3Api
@Composable
fun BookStatsCard(book: CustomBookModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .padding(3.dp),
        shape = RectangleShape, elevation = CardDefaults.cardElevation(5.dp),
    ) {
        Row(
            modifier = Modifier.padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            val imageUrl: String = book.photoUrl.toString()
                .ifEmpty { AppConstants.EMPTY_IMAGE }
            Image(
                modifier = Modifier
                    .width(80.dp)
                    .height(80.dp),
                painter = rememberAsyncImagePainter(imageUrl),
                contentDescription = "Book Img"
            )
            Column {
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = book.title.toString(), overflow = TextOverflow.Ellipsis)
                    if (book.rating!! >= 4) {
                        Spacer(modifier = Modifier.fillMaxWidth(0.8f))
                        Icon(
                            imageVector = Icons.Default.ThumbUp,
                            contentDescription = "Thumbs up",
                            tint = Color.Green.copy(alpha = 0.5f)
                        )
                    } else Box {}
                }
                Text(
                    text = "Author: ${book.authors}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Started: ${formatDate(book.startedReading!!)}",
                    softWrap = true,
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Finished ${formatDate(book.finishedReading!!)}",
                    overflow = TextOverflow.Clip,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}