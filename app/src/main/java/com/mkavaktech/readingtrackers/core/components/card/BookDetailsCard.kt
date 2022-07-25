package com.mkavaktech.readingtrackers.core.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.mkavaktech.readingtrackers.core.init.model.VolumeInfo

@ExperimentalMaterial3Api
@Composable
fun BookDetailsCard(
    modifier: Modifier,
    bookData: VolumeInfo?
) {
    Card(
        modifier = modifier.padding(5.dp),
        shape = RectangleShape, elevation = CardDefaults.cardElevation(5.dp),
    ) {
        Row(modifier = modifier.padding(10.dp)) {
            Image(
                modifier = modifier
                    .width(80.dp)
                    .height(70.dp),
                painter = rememberAsyncImagePainter(model = bookData!!.imageLinks.thumbnail),
                contentDescription = "Book Img",
                contentScale = ContentScale.Fit
            )
            Column {
                Text(
                    text = bookData.title,
                    style = MaterialTheme.typography.bodyLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 3
                )
                Text(
                    text = "Authors: ${bookData.authors}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Page: ${bookData.pageCount}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Categories: ${bookData.categories}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Published: ${bookData.publishedDate}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

    }
}