package com.mkavaktech.readingtrackers.view.main.details

import android.util.DisplayMetrics
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.text.HtmlCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.mkavaktech.readingtrackers.core.components.appbar.AppBar
import com.mkavaktech.readingtrackers.core.components.button.RoundedButton
import com.mkavaktech.readingtrackers.core.components.card.BookDetailsCard
import com.mkavaktech.readingtrackers.core.components.text.CustomTitle
import com.mkavaktech.readingtrackers.core.init.data.Resource
import com.mkavaktech.readingtrackers.core.init.model.CustomBookModel
import com.mkavaktech.readingtrackers.core.init.model.Item
import com.mkavaktech.readingtrackers.core.init.model.VolumeInfo
import com.mkavaktech.readingtrackers.core.utils.saveToFirebase
import com.mkavaktech.readingtrackers.view.main.details.viewmodel.DetailsViewModel

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun DetailsView(
    modifier: Modifier = Modifier,
    navController: NavController,
    bookId: String,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            AppBar(
                title = "Book Details",
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Back Icon",
                            tint = Color.Blue.copy(alpha = 0.7f)
                        )
                    }
                },
            )
        }) {
        it
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            DetailsContent(modifier, navController, viewModel, bookId)
        }
    }
}

@ExperimentalMaterial3Api
@ExperimentalComposeUiApi
@Composable
fun DetailsContent(
    modifier: Modifier,
    navController: NavController,
    viewModel: DetailsViewModel,
    bookId: String
) {
    Column(
        modifier = modifier
            .padding(10.dp)
    ) {
        val bookInfo = produceState<Resource<Item>>(initialValue = Resource.Loading()) {
            value = viewModel.getBookInfo(bookId)
        }.value
        Spacer(modifier = modifier.height(60.dp))
        if (bookInfo.data == null) LinearProgressIndicator() else ShowBookDetails(
            modifier,
            bookInfo,
            navController
        )
    }
}

@ExperimentalMaterial3Api
@Composable
fun ShowBookDetails(
    modifier: Modifier = Modifier,
    bookInfo: Resource<Item>,
    navController: NavController
) {
    val bookData = bookInfo.data?.volumeInfo
    val googleBookId = bookInfo.data?.id
    val cleanDescription = HtmlCompat.fromHtml(
        bookData!!.description,
        HtmlCompat.FROM_HTML_MODE_LEGACY
    ).toString()
    val localDims = LocalContext.current.resources.displayMetrics
    BookDetailsCard(modifier, bookData)
    CustomTitle(label = "Description")
    DescriptionColumn(localDims, cleanDescription)
    Spacer(modifier = Modifier.height(15.dp))
    DetailsButtonsRow(bookData, googleBookId, navController)
}

@Composable
private fun DescriptionColumn(localDims: DisplayMetrics, cleanDescription: String) {
    Surface(
        modifier = Modifier
            .height(localDims.heightPixels.dp.times(0.09f))
            .padding(4.dp), shape = RectangleShape, border = BorderStroke(1.dp, Color.DarkGray)
    ) {
        LazyColumn(modifier = Modifier.padding(4.dp)) {
            item {
                Text(text = cleanDescription, style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
private fun DetailsButtonsRow(
    bookData: VolumeInfo,
    googleBookId: String?,
    navController: NavController
) {
    Row(horizontalArrangement = Arrangement.SpaceAround) {
        RoundedButton(label = "Save") {
            val book = CustomBookModel(
                title = bookData.title,
                authors = bookData.authors.toString(),
                description = bookData.description,
                categories = bookData.categories.toString(),
                notes = "",
                photoUrl = bookData.imageLinks.thumbnail,
                publishedDate = bookData.publishedDate,
                pageCount = bookData.pageCount.toString(),
                rating = 0.0,
                googleBookId = googleBookId,
                userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
            )
            saveToFirebase(book, navController = navController)
        }
        Spacer(modifier = Modifier.width(25.dp))
        RoundedButton(label = "Cancel") { navController.popBackStack() }
    }
}


