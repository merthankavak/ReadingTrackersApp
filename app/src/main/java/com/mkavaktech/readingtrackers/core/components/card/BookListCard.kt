package com.mkavaktech.readingtrackers.core.components.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.mkavaktech.readingtrackers.core.constants.app.AppConstants
import com.mkavaktech.readingtrackers.core.constants.enums.NavigationEnums
import com.mkavaktech.readingtrackers.core.init.model.Item

@ExperimentalMaterial3Api
@Composable
fun BookListCard(
    modifier: Modifier = Modifier,
    book: Item,
    navController: NavController
) {
    Card(
        modifier = modifier
            .padding(5.dp)
            .fillMaxWidth()
            .height(145.dp)
            .clickable { navController.navigate(NavigationEnums.DetailsView.name + "/${book.id}") },
        shape = RectangleShape,
        elevation = CardDefaults.cardElevation(5.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        val imageUrl =
            book.volumeInfo.imageLinks.smallThumbnail.ifEmpty { AppConstants.EMPTY_IMAGE }
        Row(modifier = modifier.padding(15.dp)) {
            Image(
                painter = rememberAsyncImagePainter(model = imageUrl),
                contentDescription = "Book Img",
                contentScale = ContentScale.Fit
            )
            Spacer(modifier = Modifier.width(20.dp))
            Column(verticalArrangement = Arrangement.Top, horizontalAlignment = Alignment.Start) {
                Text(
                    text = book.volumeInfo.title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Authors: ${book.volumeInfo.authors}",
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "Page: ${book.volumeInfo.pageCount}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Categories: ${book.volumeInfo.categories}",
                    style = MaterialTheme.typography.bodySmall
                )
                Text(
                    text = "Published: ${book.volumeInfo.publishedDate}",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
